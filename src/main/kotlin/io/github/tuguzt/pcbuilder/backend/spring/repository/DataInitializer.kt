package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ComponentService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.UserNamePasswordService
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseDriveBays
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseExpansionSlots
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupplyShroud
import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseType
import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CpuSocket
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GpuMultiSupport
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryECCType
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryType
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.*
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.nacular.measured.units.BinarySize.Companion.gigabytes
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userNamePasswordService: UserNamePasswordService,
    private val componentsService: ComponentService,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun run(args: ApplicationArguments?): Unit = runBlocking {
        generateUsers().forEach {
            userNamePasswordService.save(it)
        }
        generateComponents().forEach { component ->
            val currentUser = generateUsers().first().let {
                UserData(it.id, it.role, it.username, it.email, it.imageUri)
            }
            val result = runCatching {
                componentsService.save(component, currentUser)
            }
            logger.info(result.exceptionOrNull()) { "Inserted data: ${result.getOrNull()}" }
        }
    }

    private fun generateUsers(): List<UserNamePasswordData> {
        val administrator = kotlin.run {
            val username = "tuguzT"
            val password = "S6iwekjbbi_92,!"
            val email = "timurka.tugushev@gmail.com"
            require(checkUsername(username) && checkPassword(password))
            UserNamePasswordData(
                role = UserRole.Administrator,
                email = email,
                imageUri = null,
                username = username,
                password = passwordEncoder.encode(password),
            )
        }
        val moderator = kotlin.run {
            val username = "dr3am_b3ast"
            val password = "Y873lin)*odjv"
            require(checkUsername(username) && checkPassword(password))
            UserNamePasswordData(
                role = UserRole.Moderator,
                email = null,
                imageUri = null,
                username = username,
                password = passwordEncoder.encode(password),
            )
        }
        return listOf(administrator, moderator)
    }

    private fun generateComponents(): List<PolymorphicComponent> = listOf(
        MotherboardData(
            name = "ASUS TUF GAMING B550M-PLUS WiFi II AM4 PCIe 4.0 WiFi 6 2.5Gb LAN BIOS FlashBack HDMI 2.1 USB 3.2 Gen 2 micro ATX",
            description = """AMD AM4 Socket and PCIe 4.0: The perfect pairing for 3rd Gen AMD Ryzen CPUs
                Robust Power Design: 8+2 DrMOS power stages with high-quality alloy chokes and durable capacitors to provide reliable power for the last AMD high-count-core CPUs
                Optimized Thermal Solution: Fanless VRM and PCH heatsink, multiple hybrid fan headers and fan speed management with Fan Xpert 4 or the UEFI Q-Fan Control utility
                High-performance Gaming Networking: WiFi 6 (802.11ax), 2.5 Gb LAN with ASUS LANGuard
                Best Gaming Connectivity: Supports HDMI 2.1 (4K@60HZ) and DisplayPort 1.2 output, featuring dual M.2 slots (NVMe SSD)— one with PCIe 4.0 x4 connectivity, front panel USB 3.2 Gen 1 connector, USB 3.2 Gen 2 Type-C & Type-A ports
                Industry-leading Gaming Audio & AI Noise Cancelling Mic Technology: High fidelity audio from a SupremeFX S1220A codec with DTS Sound Unbound and Sonic Studio III draws you deeper into the action. Communicate clearly with ASUS AI Noise Cancelling Mic technology.
                Gaming Look and Feel: ASUS-exclusive Aura Sync RGB lighting, including RGB headers and a Gen 2 addressable RGB header for greater customization""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.0 * meters, width = 0.0 * meters, height = 0.0 * meters),
            manufacturer = ManufacturerData(name = "ASUS", description = ""),
            imageUri = "https://media.pangoly.com/img/2/7/0/0/27001668-35fb-4383-b4da-1234cc4bb367.jpg",
            isFavorite = false,
            formFactor = MotherboardFormFactor.Micro_ATX,
            chipset = MotherboardChipset.AMD.B550,
            cpuSocket = MotherboardCpuSocket.Standard(socket = CpuSocket.AM4),
            memoryType = MemoryType.DDR4,
            memoryECCType = MemoryECCType.NonECC,
            memoryAmount = MotherboardMemoryAmount(amount = 128.0 * gigabytes),
            memorySlotCount = MotherboardMemorySlotCount(count = 4u),
            multiGpuSupport = null,
            slots = MotherboardSlots(
                pciExpressX16Count = 2u,
                pciExpressX8Count = 0u,
                pciExpressX4Count = 0u,
                pciExpressX1Count = 1u,
                pciCount = 0u,
                m2Count = 2u,
                mSataCount = 0u,
            ),
            ports = MotherboardPorts(sata3GBpSecCount = 0u, sata6GBpSecCount = 4u),
            usbHeaders = MotherboardUsbHeaders(
                usb2HeaderCount = 2u,
                usb3gen1HeaderCount = 1u,
                usb3gen2HeaderCount = 0u,
                usb3gen2x2HeaderCount = 0u,
            ),
        ),
        CaseData(
            name = "NZXT H7 Flow White/Black ATX Mid Tower, Front I/O USB Type-C Port, Tempered Glass Side Panel CM-H71FG-01",
            description = """BETTER THERMALS: opened up the top panel to achieve even better thermal efficiency. The perforated panel provides improved ventilation as warm air flows through the top of the chassis.
                RADIATORS: The top and front of the case support radiators up to 360mm, while the front panel can accommodate three 140mm fans for maximum cooling.
                BUILDING SIMPLIFIED: Streamline the build process with an improved cable management system. Wider cable channels provide more room to easily route cables, while the addition of hooks add stability.
                A MODERN LOOK: The H7 combines the modern look of the H series with new color options that fit in seamlessly with any aesthetic. Each color is paired with glass tinting that complements the respective chassis.
                MORE SPACE: Ample space and clearance make the H7 a spacious chassis for ambitious builds.""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.48 * meters, width = 0.23 * meters, height = 0.505 * meters),
            manufacturer = ManufacturerData(name = "NZXT", description = ""),
            imageUri = "https://media.pangoly.com/img/d/d/c/c/ddccebde-14b9-4ef0-a52a-07d0f9d05e66.jpg",
            isFavorite = false,
            driveBays = CaseDriveBays(
                external5_25_count = 0u,
                external3_5_count = 0u,
                internal3_5_count = 4u,
                internal2_5_count = 2u,
            ),
            expansionSlots = CaseExpansionSlots(fullHeightCount = 7u, halfHeightCount = 0u),
            motherboardFormFactors = listOf(
                MotherboardFormFactor.ATX,
                MotherboardFormFactor.EATX,
                MotherboardFormFactor.Micro_ATX,
                MotherboardFormFactor.Mini_ITX,
            ),
            powerSupply = null,
            powerSupplyShroud = CasePowerSupplyShroud.NonShroud,
            sidePanelWindow = null,
            caseType = CaseType.ATX.Mid_Tower,
        ),
        MotherboardData(
            name = "ASUS TUF Gaming Z690-Plus WiFi LGA 1700 PCIe 5.0 DDR5 4x NVMe SSD 14+2 Power Stages WiFi 6 2.5Gb LAN Front USB 3.2 Gen 2 Type-C Ports Thunderbolt ATX",
            description = """Intel LGA 1700 socket: Ready for 12th Gen Intel Core processors, support PCIe 5.0, DDR5 and out of box Windows 11 ready
                Enhanced Power Solution: 14+2 DrMOS power stages, ProCool sockets, military-grade TUF components, and Digi+ VRM for maximum durability and performance
                Comprehensive Cooling : VRM heatsink, PCH fanless heatsink, M.2 heatsink, hybrid fan headers and Fan Xpert 4 utility
                Ultra Fast Gaming Networking : WiFi 6 AX201 (802.11 ax), Intel I225-V 2.5Gb LAN, TUF LANGuard and TurboLAN technology
                Fastest Connectivity: 4x M.2/NVMe SSD, Front panel USB 3.2 Gen 2 Type-C header, USB Gen 2x2 Type-C and Thunderbolt 4 header
                PC DIY Friendly: SafeDIMM, PCIe 5.0 Safeslot, Q-LED, M.2 Q-Latch, Pre-mount I/O shield
                New TUF Gaming Aesthetics: New ID design, Synchronizable LED effects across a vast portfolio of compatible PC gear, including addressable RGB strips with Aura Sync
                Immersive Gaming Audio & AI Noise Cancellation: The Realtek S1200A codec offers pristine audio quality to draw you deeper into the game action or to enhance your favorite music tracks or videos. Communicate clearly with ASUS AI Noise Canceling Mic technology""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.0 * meters, width = 0.0 * meters, height = 0.0 * meters),
            manufacturer = ManufacturerData(name = "ASUS", description = ""),
            imageUri = "https://media.pangoly.com/img/1/e/a/5/1ea5a37b-fa1e-4f70-920b-a279fa26f1c9.jpg",
            isFavorite = false,
            formFactor = MotherboardFormFactor.ATX,
            chipset = MotherboardChipset.Intel.Z690,
            cpuSocket = MotherboardCpuSocket.Standard(socket = CpuSocket.LGA1700),
            memoryType = MemoryType.DDR5,
            memoryECCType = MemoryECCType.NonECC,
            memoryAmount = MotherboardMemoryAmount(amount = 128.0 * gigabytes),
            memorySlotCount = MotherboardMemorySlotCount(count = 4u),
            multiGpuSupport = null,
            slots = MotherboardSlots(
                pciExpressX16Count = 2u,
                pciExpressX8Count = 0u,
                pciExpressX4Count = 1u,
                pciExpressX1Count = 2u,
                pciCount = 0u,
                m2Count = 4u,
                mSataCount = 0u,
            ),
            ports = MotherboardPorts(sata3GBpSecCount = 0u, sata6GBpSecCount = 4u),
            usbHeaders = MotherboardUsbHeaders(
                usb2HeaderCount = 2u,
                usb3gen1HeaderCount = 1u,
                usb3gen2HeaderCount = 1u,
                usb3gen2x2HeaderCount = 0u,
            ),
        ),
        CaseData(
            name = "NZXT H7 Flow White ATX Mid Tower, Front I/O USB Type-C Port, Tempered Glass Side Panel CM-H71FW-01",
            description = """BETTER THERMALS: opened up the top panel to achieve even better thermal efficiency. The perforated panel provides improved ventilation as warm air flows through the top of the chassis.
                RADIATORS: The top and front of the case support radiators up to 360mm, while the front panel can accommodate three 140mm fans for maximum cooling.
                BUILDING SIMPLIFIED: Streamline the build process with an improved cable management system. Wider cable channels provide more room to easily route cables, while the addition of hooks add stability.
                A MODERN LOOK: The H7 combines the modern look of the H series with new color options that fit in seamlessly with any aesthetic. Each color is paired with glass tinting that complements the respective chassis.
                MORE SPACE: Ample space and clearance make the H7 a spacious chassis for ambitious builds.""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.48 * meters, width = 0.23 * meters, height = 0.505 * meters),
            manufacturer = ManufacturerData(name = "NZXT", description = ""),
            imageUri = "https://media.pangoly.com/img/1/e/c/9/1ec9b318-7ff8-4ef7-b474-63a0f96a2937.jpg",
            isFavorite = false,
            driveBays = CaseDriveBays(
                external5_25_count = 0u,
                external3_5_count = 0u,
                internal3_5_count = 4u,
                internal2_5_count = 2u,
            ),
            expansionSlots = CaseExpansionSlots(fullHeightCount = 7u, halfHeightCount = 0u),
            motherboardFormFactors = listOf(
                MotherboardFormFactor.ATX,
                MotherboardFormFactor.EATX,
                MotherboardFormFactor.Micro_ATX,
            ),
            powerSupply = null,
            powerSupplyShroud = CasePowerSupplyShroud.NonShroud,
            sidePanelWindow = null,
            caseType = CaseType.ATX.Mid_Tower,
        ),
        CaseData(
            name = "NZXT H7 White/Black ATX Mid Tower, Front I/O USB Type-C Port, Tempered Glass Side Panel CM-H71BG-01",
            description = """BETTER THERMALS: opened up the top panel to achieve even better thermal efficiency. The perforated panel provides improved ventilation as warm air flows through the top of the chassis.
                RADIATORS: The top and front of the case support radiators up to 360mm, while the front panel can accommodate three 140mm fans for maximum cooling.
                BUILDING SIMPLIFIED: Streamline the build process with an improved cable management system. Wider cable channels provide more room to easily route cables, while the addition of hooks add stability.
                A MODERN LOOK: The H7 combines the modern look of the H series with new color options that fit in seamlessly with any aesthetic. Each color is paired with glass tinting that complements the respective chassis.
                MORE SPACE: Ample space and clearance make the H7 a spacious chassis for ambitious builds.""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.48 * meters, width = 0.23 * meters, height = 0.505 * meters),
            manufacturer = ManufacturerData(name = "NZXT", description = ""),
            imageUri = "https://media.pangoly.com/img/7/e/6/f/7e6fc5bf-6ce6-4723-b2f0-f1604b453529.jpg",
            isFavorite = false,
            driveBays = CaseDriveBays(
                external5_25_count = 0u,
                external3_5_count = 0u,
                internal3_5_count = 4u,
                internal2_5_count = 2u,
            ),
            expansionSlots = CaseExpansionSlots(fullHeightCount = 7u, halfHeightCount = 0u),
            motherboardFormFactors = listOf(
                MotherboardFormFactor.ATX,
                MotherboardFormFactor.EATX,
                MotherboardFormFactor.Micro_ATX,
                MotherboardFormFactor.Mini_ITX,
            ),
            powerSupply = null,
            powerSupplyShroud = CasePowerSupplyShroud.NonShroud,
            sidePanelWindow = null,
            caseType = CaseType.ATX.Mid_Tower,
        ),
        MotherboardData(
            name = "GIGABYTE X570S AORUS Elite AX PCIe 4.0 SATA 6Gb/s USB 3.1 ATX",
            description = """Supports AMD Ryzen 5th Gen/ 4th Gen/ 3rd Gen/ 2nd Gen
                Dual Channel ECC/ Non-ECC Unbuffered DDR4, 4 DIMMs
                Twin 12+2 Phases Digital VRM Solution with 60A DrMOS
                Fully Covered Thermal Design with High Coverage MOSFET Heatsinks
                AMD WiFi 6E 802.11ax & BT 5.2
                Triple Ultra-Fast NVMe PCIe 4.0/3.0 x4 M.2 with Thermal Guards
                Fast 2.5GbE LAN with Bandwidth Management
                SuperSpeed USB 3.2 Gen 2x2 TYPE-C delivers up to 20Gb/s transfer speeds
                AMP-UP Audio with ALC1220-VB and WIMA Audio Capacitors
                Smart Fan 6 Features Multiple Temperature Sensors, Hybrid Fan Headers with FAN STOP""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.0 * meters, width = 0.0 * meters, height = 0.0 * meters),
            manufacturer = ManufacturerData(name = "Gigabyte", description = ""),
            imageUri = "https://media.pangoly.com/img/f/f/4/7/ff474eba-cbb0-41d3-9d8a-6540b431630e.jpg",
            isFavorite = false,
            formFactor = MotherboardFormFactor.ATX,
            chipset = MotherboardChipset.AMD.X570,
            cpuSocket = MotherboardCpuSocket.Standard(socket = CpuSocket.AM4),
            memoryType = MemoryType.DDR4,
            memoryECCType = MemoryECCType.NonECC,
            memoryAmount = MotherboardMemoryAmount(amount = 128.0 * gigabytes),
            memorySlotCount = MotherboardMemorySlotCount(count = 4u),
            multiGpuSupport = GpuMultiSupport.CrossFireX(wayCount = GpuMultiSupport.CrossFireX.WayCount.TwoWay),
            slots = MotherboardSlots(
                pciExpressX16Count = 3u,
                pciExpressX8Count = 0u,
                pciExpressX4Count = 0u,
                pciExpressX1Count = 0u,
                pciCount = 0u,
                m2Count = 3u,
                mSataCount = 0u,
            ),
            ports = MotherboardPorts(sata3GBpSecCount = 0u, sata6GBpSecCount = 6u),
            usbHeaders = MotherboardUsbHeaders(
                usb2HeaderCount = 2u,
                usb3gen1HeaderCount = 2u,
                usb3gen2HeaderCount = 1u,
                usb3gen2x2HeaderCount = 0u,
            ),
        ),
        CaseData(
            name = "NZXT H7 White ATX Mid Tower, Front I/O USB Type-C Port, Tempered Glass Side Panel CM-H71BW-01",
            description = """BETTER THERMALS: opened up the top panel to achieve even better thermal efficiency. The perforated panel provides improved ventilation as warm air flows through the top of the chassis.
                RADIATORS: The top and front of the case support radiators up to 360mm, while the front panel can accommodate three 140mm fans for maximum cooling.
                BUILDING SIMPLIFIED: Streamline the build process with an improved cable management system. Wider cable channels provide more room to easily route cables, while the addition of hooks add stability.
                A MODERN LOOK: The H7 combines the modern look of the H series with new color options that fit in seamlessly with any aesthetic. Each color is paired with glass tinting that complements the respective chassis.
                MORE SPACE: Ample space and clearance make the H7 a spacious chassis for ambitious builds.""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.48 * meters, width = 0.23 * meters, height = 0.505 * meters),
            manufacturer = ManufacturerData(name = "NZXT", description = ""),
            imageUri = "https://media.pangoly.com/img/1/3/8/3/1383bd27-0b80-4b5e-b15c-37f307907309.jpg",
            isFavorite = false,
            driveBays = CaseDriveBays(
                external5_25_count = 0u,
                external3_5_count = 0u,
                internal3_5_count = 4u,
                internal2_5_count = 2u,
            ),
            expansionSlots = CaseExpansionSlots(fullHeightCount = 7u, halfHeightCount = 0u),
            motherboardFormFactors = listOf(
                MotherboardFormFactor.ATX,
                MotherboardFormFactor.EATX,
                MotherboardFormFactor.Micro_ATX,
                MotherboardFormFactor.Mini_ITX,
            ),
            powerSupply = null,
            powerSupplyShroud = CasePowerSupplyShroud.NonShroud,
            sidePanelWindow = null,
            caseType = CaseType.ATX.Mid_Tower,
        ),
        MotherboardData(
            name = "GIGABYTE B660 DS3H 1700 4X DDR4 ATX",
            description = "GIGABYTE B660 DS3H 1700 ATX 4X DDR4",
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.0 * meters, width = 0.0 * meters, height = 0.0 * meters),
            manufacturer = ManufacturerData(name = "Gigabyte", description = ""),
            imageUri = "https://media.pangoly.com/img/b/e/6/f/be6f5553-8ab9-4bf5-b010-6982ad429989.jpg",
            isFavorite = false,
            formFactor = MotherboardFormFactor.ATX,
            chipset = MotherboardChipset.Intel.B660,
            cpuSocket = MotherboardCpuSocket.Standard(socket = CpuSocket.LGA1700),
            memoryType = MemoryType.DDR4,
            memoryECCType = MemoryECCType.NonECC,
            memoryAmount = MotherboardMemoryAmount(amount = 128.0 * gigabytes),
            memorySlotCount = MotherboardMemorySlotCount(count = 4u),
            multiGpuSupport = null,
            slots = MotherboardSlots(
                pciExpressX16Count = 1u,
                pciExpressX8Count = 0u,
                pciExpressX4Count = 0u,
                pciExpressX1Count = 2u,
                pciCount = 0u,
                m2Count = 2u,
                mSataCount = 0u,
            ),
            ports = MotherboardPorts(sata3GBpSecCount = 0u, sata6GBpSecCount = 4u),
            usbHeaders = MotherboardUsbHeaders(
                usb2HeaderCount = 2u,
                usb3gen1HeaderCount = 1u,
                usb3gen2HeaderCount = 1u,
                usb3gen2x2HeaderCount = 0u,
            ),
        ),
        MotherboardData(
            name = "EVGA X570 FTW WiFi 121-VR-A577-KR AM4 PCIe Gen4 SATA 6Gb/s Wi-Fi 6/BT5.2 USB 3.2 Gen2 M.2 ATX",
            description = """AMD X570 Chipset, Supports AMD Ryzen 5000 and 3000 Series processors for the AMD AM4 socket
                PCIe Gen4, M.2 Gen4, ATX Form Factor, 15 Phase PWM, Metal Reinforced PCIe Slots, NVIDIA SLI Ready, Preinstalled rear I/O cover
                4 DIMM Dual-Channel supports up to 128GB 4600+ MHz, 3 USB 3.2 Gen2 Ports (2x Type A, 1x Type-C), DP 1.4/HDMI 2.0
                8 SATA 6Gb/s, 2x M.2 Key-M 110mm, 1x M.2 Key-E 32mm with installed Wi-Fi 6/BT 5.2, Intel Gigabit NIC
                Onboard ARGB lighting + 2x ARGB / 2x RGB Headers fully customizable through EVGA ELEET X1""".trimIndent(),
            weight = Weight(weight = 0.0 * grams),
            size = Size(length = 0.0 * meters, width = 0.0 * meters, height = 0.0 * meters),
            manufacturer = ManufacturerData(name = "EVGA", description = ""),
            imageUri = "https://media.pangoly.com/img/c/8/3/f/c83f0797-8d0b-4c9e-bdc2-fd1d617e3b92.jpg",
            isFavorite = false,
            formFactor = MotherboardFormFactor.ATX,
            chipset = MotherboardChipset.AMD.X570,
            cpuSocket = MotherboardCpuSocket.Standard(socket = CpuSocket.AM4),
            memoryType = MemoryType.DDR4,
            memoryECCType = MemoryECCType.NonECC,
            memoryAmount = MotherboardMemoryAmount(amount = 128.0 * gigabytes),
            memorySlotCount = MotherboardMemorySlotCount(count = 4u),
            multiGpuSupport = GpuMultiSupport.SLI(wayCount = GpuMultiSupport.SLI.WayCount.TwoWay),
            slots = MotherboardSlots(
                pciExpressX16Count = 2u,
                pciExpressX8Count = 0u,
                pciExpressX4Count = 0u,
                pciExpressX1Count = 0u,
                pciCount = 0u,
                m2Count = 3u,
                mSataCount = 0u,
            ),
            ports = MotherboardPorts(sata3GBpSecCount = 0u, sata6GBpSecCount = 8u),
            usbHeaders = MotherboardUsbHeaders(
                usb2HeaderCount = 2u,
                usb3gen1HeaderCount = 1u,
                usb3gen2HeaderCount = 0u,
                usb3gen2x2HeaderCount = 0u,
            ),
        ),
    )
}
