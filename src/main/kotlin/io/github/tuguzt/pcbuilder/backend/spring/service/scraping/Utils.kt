package io.github.tuguzt.pcbuilder.backend.spring.service.scraping

import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CpuSocket
import io.github.tuguzt.pcbuilder.domain.model.component.memory.MemoryType
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardChipset
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardMemoryAmount
import io.nacular.measured.units.BinarySize.Companion.gigabytes
import io.nacular.measured.units.times

fun String.toMotherboardFormFactors(): List<MotherboardFormFactor> {
    val strings = split(",").map { it.trim() }
    return MotherboardFormFactor.values().filter { "$it" in strings }
}

fun String.toMotherboardFormFactor(): MotherboardFormFactor? =
    MotherboardFormFactor.values().firstOrNull { "$it" == this }

fun String.toCpuSocket(): CpuSocket? =
    CpuSocket.values().firstOrNull { "$it" == this }

fun String.toMotherboardChipset(): MotherboardChipset? =
    MotherboardChipset.CPUIntegrated.takeIf { "$it" == this }
        ?: MotherboardChipset.AMD.values().firstOrNull { "$it" == this }
        ?: MotherboardChipset.Intel.values().firstOrNull { "$it" == this }
        ?: MotherboardChipset.VIA.values().firstOrNull { "$it" == this }
        ?: MotherboardChipset.NVIDIA.GeForce.values().firstOrNull { it.name.substringAfter("GeForce") in this }
        ?: MotherboardChipset.NVIDIA.NForce.values().firstOrNull { it.name.substringAfter("NForce") in this }
        ?: MotherboardChipset.NVIDIA.ION.takeIf { "ION" in this }

fun String.toMemoryType(): MemoryType? =
    MemoryType.values().reversedArray().firstOrNull { "$it" in substringBefore(':') }

fun String.toMotherboardMemoryAmount(): MotherboardMemoryAmount? =
    split(' ').firstOrNull()?.toDoubleOrNull()?.let { MotherboardMemoryAmount(it * gigabytes) }
