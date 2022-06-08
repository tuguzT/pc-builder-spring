package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardChipset
import javax.persistence.Embeddable

@Embeddable
data class MotherboardChipsetEmbeddable(private val chipsetName: String) {
    fun toMotherboardChipset(): MotherboardChipset =
        MotherboardChipset.Intel.values().firstOrNull { "$it" == chipsetName }
            ?: MotherboardChipset.AMD.values().firstOrNull { "$it" == chipsetName }
            ?: MotherboardChipset.VIA.values().firstOrNull { "$it" == chipsetName }
            ?: MotherboardChipset.NVIDIA.NForce.values().firstOrNull { "$it" == chipsetName }
            ?: MotherboardChipset.NVIDIA.GeForce.values().firstOrNull { "$it" == chipsetName }
            ?: MotherboardChipset.NVIDIA.ION?.takeIf { "$it" == chipsetName }
            ?: MotherboardChipset.CPUIntegrated
}

fun MotherboardChipset.toEmbeddable(): MotherboardChipsetEmbeddable =
    MotherboardChipsetEmbeddable(toString())
