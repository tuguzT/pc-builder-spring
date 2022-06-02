package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.eachHref
import it.skrape.selects.html5.*
import kotlinx.coroutines.*
import mu.KLogger
import mu.KotlinLogging
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
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
        randomDelay(5.0..10.0)
        request {
            url = "https://pangoly.com$path?page=$page"
            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36"
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
        randomDelay(5.0..10.0)
        request {
            url = itemUrl
            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36"
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
                ParseRawData(name, manufacturerName, data)
            }
        }
    }

    @PostConstruct
    private fun scrapeCases() {
        scrapingJob = coroutineScope.launch {
            while (isActive) {
                tailrec suspend fun task(page: UInt = 1u) {
                    val itemRefs = itemsFromPage(page)
                    itemRefs.forEach { itemUrl ->
                        logger.info { "Item URL: $itemUrl" }
                        val data = dataFromItem(itemUrl)
                        val case = parse(data)
                        logger.info { "Scraped data: $case" }
                    }
                    logger.info { "Page $page scraped" }
                    task(page + 1u)
                }

                val result = kotlin.runCatching { task() }
                result.exceptionOrNull()?.let {
                    logger.error(it) { "Scraping error" }
                }
            }
        }
    }

    private var scrapingJob: Job? = null

    @PreDestroy
    private fun shutdown() = runBlocking {
        scrapingJob?.cancelAndJoin()
    }
}
