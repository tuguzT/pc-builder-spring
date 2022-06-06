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
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.nacular.measured.units.Length.Companion.millimeters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.coroutines.CoroutineScope
import org.springframework.stereotype.Service

@Service
class CaseScrapingService(
    coroutineScope: CoroutineScope,
    private val caseService: CaseService,
    private val manufacturerService: ManufacturerService,
    private val formFactorService: MotherboardFormFactorService,
) : AbstractScrapingService<CaseData>(path = "/en/browse/case", coroutineScope) {

    override suspend fun parse(data: ParseRawData): CaseData? {
        val (name, manufacturerName, imageUris, map) = data
        if (map.isEmpty()) return null

        val type = map["Type"]?.toCaseType() ?: return null
        val powerSupply = CasePowerSupply(power = 1 * watt).takeIf { map["Includes Power Supply"] == "Yes" }
        val external5x25Count = map["External 5.25\" Drive Bays"]?.toUInt() ?: 0u
        val external3x5Count = map["External 3.5\" Drive Bays"]?.toUInt() ?: 0u
        val internal3x5Count = map["Internal 2.5\" Drive Bays"]?.toUInt() ?: 0u
        val internal2x5Count = map["Internal 3.5\" Drive Bays"]?.toUInt() ?: 0u
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
            ?.toMotherboardFormFactors()
            ?: emptyList()

        val manufacturer = kotlin.run {
            val id = manufacturerService.findByName(manufacturerName)?.id ?: randomNanoId()
            val manufacturer = ManufacturerData(id, manufacturerName, description = "")
            manufacturerService.save(manufacturer)
        }
        return kotlin.run {
            val id = caseService.findByName(name, currentUser = null)?.id ?: randomNanoId()
            val case = CaseData(
                id = id,
                name = name,
                description = "",
                weight = Weight(0 * grams),
                size = size,
                imageUri = imageUris.firstOrNull(),
                isFavorite = false,
                manufacturer = manufacturer,
                driveBays = CaseDriveBays(
                    external5x25Count,
                    external3x5Count,
                    internal3x5Count,
                    internal2x5Count,
                ),
                expansionSlots = CaseExpansionSlots(expansionSlots, 0u),
                caseType = type,
                powerSupplyShroud = CasePowerSupplyShroud.NonShroud,
                sidePanelWindow = null,
                powerSupply = powerSupply,
                motherboardFormFactors = motherboardFormFactors
                    .map { formFactorService.save(it.toEntity()).id },
            )
            caseService.save(case, currentUser = null)
        }
    }
}
