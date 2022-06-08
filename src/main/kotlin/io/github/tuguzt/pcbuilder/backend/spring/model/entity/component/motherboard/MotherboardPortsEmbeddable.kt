package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardPorts
import javax.persistence.Embeddable

@Embeddable
data class MotherboardPortsEmbeddable(
    private val sata3GBpSecCount: UShort,
    private val sata6GBpSecCount: UShort,
) {
    fun toMotherboardPorts(): MotherboardPorts = MotherboardPorts(sata3GBpSecCount, sata6GBpSecCount)
}

fun MotherboardPorts.toEmbeddable(): MotherboardPortsEmbeddable =
    MotherboardPortsEmbeddable(sata3GBpSecCount, sata6GBpSecCount)
