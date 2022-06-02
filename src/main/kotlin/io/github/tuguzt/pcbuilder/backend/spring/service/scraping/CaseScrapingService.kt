package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.toCaseType
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.CaseService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ManufacturerService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.MotherboardFormFactorService
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseDriveBays
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseExpansionSlots
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupply
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupplyShroud
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.eachHref
import it.skrape.selects.html5.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class CaseScrapingService(
    private val caseService: CaseService,
    private val manufacturerService: ManufacturerService,
    private val formFactorService: MotherboardFormFactorService,
    private val coroutineScope: CoroutineScope,
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private suspend fun itemsFromPage(page: UInt = 1u): List<String> = skrape(AsyncFetcher) {
        request { url = "https://pangoly.com/en/browse/case?page=$page" }
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
        request { url = itemUrl }
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

    private suspend fun parse(data: ParseRawData): CaseData? {
        val (name, manufacturerName, map) = data
        if (map.isEmpty()) return null

        val type = map["Type"]?.toCaseType() ?: return null
        val powerSupply = CasePowerSupply(power = 1 * watt).takeIf { map["Includes Power Supply"] == "Yes" }
        val external5_25_count = map["External 5.25\" Drive Bays"]?.toUInt() ?: 0u
        val external3_5_count = map["External 3.5\" Drive Bays"]?.toUInt() ?: 0u
        val internal3_5_count = map["Internal 2.5\" Drive Bays"]?.toUInt() ?: 0u
        val internal2_5_count = map["Internal 3.5\" Drive Bays"]?.toUInt() ?: 0u
        val expansionSlots = map["Expansion Slots"]?.toUInt() ?: 0u
        val size = kotlin.run {
            val doubles = map["Dimensions"]
                ?.split("x")
                ?.map { it.trim().substringBefore("mm").toDouble() }
                ?.take(3)
                ?: listOf(0.0, 0.0, 0.0)
            val length = doubles[0] * millimeters
            val width = doubles[1] * millimeters
            val height = doubles[2] * millimeters
            Size(length, width, height)
        }
        val motherboardFormFactors = map["Motherboard Compatibility"]
            ?.toMotherboardFormFactor()
            ?: emptyList()

        val manufacturer = kotlin.run {
            val id = manufacturerService.findByName(manufacturerName)?.id ?: randomNanoId()
            val manufacturer = ManufacturerData(id, manufacturerName, description = "")
            manufacturerService.save(manufacturer)
        }
        return kotlin.run {
            val id = caseService.findByName(name)?.id ?: randomNanoId()
            val case = CaseData(
                id = id,
                name = name,
                description = "",
                weight = Weight(0 * grams),
                size = size,
                manufacturer = manufacturer,
                driveBays = CaseDriveBays(
                    external5_25_count,
                    external3_5_count,
                    internal3_5_count,
                    internal2_5_count,
                ),
                expansionSlots = CaseExpansionSlots(expansionSlots, 0u),
                type = type,
                powerSupplyShroud = CasePowerSupplyShroud.NonShroud,
                sidePanelWindow = null,
                powerSupply = powerSupply,
                motherboardFormFactors = motherboardFormFactors
                    .map { formFactorService.save(it.toEntity()).id },
            )
            caseService.save(case)
        }
    }

    @PostConstruct
    fun scrapeCases() {
        coroutineScope.launch {
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
}

private fun String.toMotherboardFormFactor(): List<MotherboardFormFactor> {
    val strings = split(",").map { it.trim() }
    return MotherboardFormFactor.values().filter { "$it" in strings }
}
