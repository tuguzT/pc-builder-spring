package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.eachAttribute
import it.skrape.selects.eachHref
import it.skrape.selects.html5.*
import kotlinx.coroutines.*
import mu.KLogger
import mu.KotlinLogging
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

sealed class AbstractScrapingService<T>(
    private val path: String,
    private val coroutineScope: CoroutineScope,
    private val logger: KLogger = KotlinLogging.logger {},
) {
    protected abstract suspend fun parse(data: ParseRawData): T?

    private suspend fun randomDelay(seconds: ClosedFloatingPointRange<Double>) {
        val duration = Random.nextDouble(from = seconds.start, until = seconds.endInclusive)
        delay(duration.seconds)
    }

    private suspend fun itemsFromPage(page: UInt = 1u): List<String> = skrape(AsyncFetcher) {
        randomDelay(5.0..20.0)
        request {
            url = "https://pangoly.com$path?page=$page"
            userAgent =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36"
        }
        val itemRefs = response {
            htmlDocument {
                a {
                    withClass = "productItemLink"
                    findAll { eachHref }
                }
            }
        }
        return@skrape itemRefs
    }

    private suspend fun dataFromItem(itemUrl: String): ParseRawData = skrape(AsyncFetcher) {
        randomDelay(5.0..20.0)
        request {
            url = itemUrl
            userAgent =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36"
        }
        response {
            htmlDocument {
                val manufacturerName = ol {
                    withClass = "breadcrumb"
                    li { 2 { text } }
                }
                val name = div {
                    withClass = "product-info"
                    h2 { findFirst { text } }
                }
                val description = div {
                    withClass = "product-info"
                    ul {
                        withClass = "list-group"
                        li { this }.findAll { this }.joinToString(separator = "\n") { it.text }
                    }
                }
                val imageUris = img {
                    withClass = "tns-lazy-img"
                    findAll { eachAttribute("data-src") }
                }
                val data = table {
                    withClass = "table"
                    tbody { this }.tr { this }.findAll { this }.associate {
                        it.td {
                            val key = findFirst { text }
                            val value = findLast {
                                text.ifEmpty {
                                    children { first().attributes["title"]!! }
                                }
                            }
                            key to value
                        }
                    }
                }
                ParseRawData(name, description, manufacturerName, imageUris, data)
            }
        }
    }

    fun start() {
        scrapingJob?.cancel()
        scrapingJob = coroutineScope.launch {
            while (isActive) {
                tailrec suspend fun task(page: UInt = 1u) {
                    val itemRefs = itemsFromPage(page)
                    itemRefs.forEach { itemUrl ->
                        val result = runCatching { dataFromItem(itemUrl) }
                        result.exceptionOrNull()?.let {
                            logger.error(it) { "Item scraping error" }
                        }
                        result.getOrNull()?.let {
                            val case = parse(it)
                            logger.info { "Item URL: ${itemUrl}\nScraped data: $case" }
                        }
                    }
                    logger.info { "Page $page scraped" }
                    task(page + 1u)
                }

                val result = kotlin.runCatching { task() }
                result.exceptionOrNull()?.let {
                    logger.error(it) { "Page scraping error" }
                }
            }
        }
    }

    private var scrapingJob: Job? = null

    fun stop() = runBlocking {
        scrapingJob?.cancelAndJoin()
    }
}
