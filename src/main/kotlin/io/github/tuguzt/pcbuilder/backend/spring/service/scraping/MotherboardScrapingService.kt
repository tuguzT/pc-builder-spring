package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ManufacturerService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.MotherboardService
import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GpuMultiSupport
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryECCType
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.*
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.coroutines.CoroutineScope
import org.springframework.stereotype.Service

@Service
class MotherboardScrapingService(
    coroutineScope: CoroutineScope,
    private val motherboardService: MotherboardService,
    private val manufacturerService: ManufacturerService,
) : AbstractScrapingService<MotherboardData>(path = "/en/browse/motherboard", coroutineScope) {

    override suspend fun parse(data: ParseRawData): MotherboardData? {
        val (name, manufacturerName, imageUris, map) = data
        if (map.isEmpty()) return null

        val motherboardFormFactor = map["Form factor"]?.toMotherboardFormFactor() ?: return null
        val cpuSocket = map["Socket"]?.toCpuSocket() ?: return null
        val chipset = map["Chipset"]?.toMotherboardChipset() ?: return null
        val memoryType = map["Supported Memory"]?.toMemoryType() ?: return null
        val memoryAmount = map["Maximum Supported RAM"]?.toMotherboardMemoryAmount() ?: return null
        val memorySlotCount = map["RAM Slots"]?.toUIntOrNull() ?: 0u
        val sliSupport = map["SLI Support"] == "Yes"
        val crossfireSupport = map["CrossFire Support"] == "Yes"
        val memoryECCType = when (map["ECC RAM Support"]) {
            "Yes" -> MemoryECCType.ECC
            else -> MemoryECCType.NonECC
        }
        val usb2HeaderCount = map["Onboard USB 2.0"]?.toUByteOrNull() ?: 0u
        val usb3gen1HeaderCount = map["Onboard USB 3.2 Gen 1"]?.toUByteOrNull() ?: 0u
        val usb3gen2HeaderCount = map["Onboard USB 3.2 Gen 2"]?.toUByteOrNull() ?: 0u
        val usb3gen2x2HeaderCount: UByte = 0u

        val manufacturer = kotlin.run {
            val id = manufacturerService.findByName(manufacturerName)?.id ?: randomNanoId()
            val manufacturer = ManufacturerData(id, manufacturerName, description = "")
            manufacturerService.save(manufacturer)
        }
        return kotlin.run {
            val id = motherboardService.findByName(name, currentUser = null)?.id ?: randomNanoId()
            val motherboard = MotherboardData(
                id = id,
                name = name,
                description = "",
                weight = Weight(weight = 0 * grams),
                size = Size(length = 0 * meters, width = 0 * meters, height = 0 * meters),
                manufacturer = manufacturer,
                imageUri = imageUris.firstOrNull(),
                isFavorite = false,
                formFactor = motherboardFormFactor,
                chipset = chipset,
                cpuSocket = MotherboardCpuSocket.Standard(cpuSocket),
                memoryType = memoryType,
                memoryAmount = memoryAmount,
                memoryECCType = memoryECCType,
                memorySlotCount = MotherboardMemorySlotCount(memorySlotCount),
                multiGpuSupport = when {
                    sliSupport -> GpuMultiSupport.SLI(GpuMultiSupport.SLI.WayCount.TwoWay)
                    crossfireSupport -> GpuMultiSupport.CrossFireX(GpuMultiSupport.CrossFireX.WayCount.TwoWay)
                    else -> null
                },
                slots = MotherboardSlots(
                    pciCount = 0u,
                    pciExpressX1Count = map["PCI-Express x1 Slots"]?.toUShortOrNull() ?: 0u,
                    pciExpressX4Count = map["PCI-Express x4 Slots"]?.toUShortOrNull() ?: 0u,
                    pciExpressX8Count = map["PCI-Express x8 Slots"]?.toUShortOrNull() ?: 0u,
                    pciExpressX16Count = map["PCI-Express x16 Slots"]?.toUShortOrNull() ?: 0u,
                    m2Count = map["M.2 Ports"]?.toUShortOrNull() ?: 0u,
                    mSataCount = 0u,
                ),
                ports = MotherboardPorts(
                    sata3GBpSecCount = 0u,
                    sata6GBpSecCount = map["SATA 6 Gbps Ports"]?.toUShortOrNull() ?: 0u,
                ),
                usbHeaders = MotherboardUsbHeaders(
                    usb2HeaderCount = usb2HeaderCount,
                    usb3gen1HeaderCount = usb3gen1HeaderCount,
                    usb3gen2HeaderCount = usb3gen2HeaderCount,
                    usb3gen2x2HeaderCount = usb3gen2x2HeaderCount,
                ),
            )
            motherboardService.save(motherboard, currentUser = null)
        }
    }
}
