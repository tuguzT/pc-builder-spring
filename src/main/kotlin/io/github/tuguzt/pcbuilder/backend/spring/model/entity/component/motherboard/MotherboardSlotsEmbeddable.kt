package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardSlots
import javax.persistence.Embeddable

@Embeddable
data class MotherboardSlotsEmbeddable(
    private val pciExpressX16Count: UShort,
    private val pciExpressX8Count: UShort,
    private val pciExpressX4Count: UShort,
    private val pciExpressX1Count: UShort,
    private val pciCount: UShort,
    private val m2Count: UShort,
    private val mSataCount: UShort,
) {
    fun toMotherboardSlots(): MotherboardSlots = MotherboardSlots(
        pciExpressX16Count,
        pciExpressX8Count,
        pciExpressX4Count,
        pciExpressX1Count,
        pciCount,
        m2Count,
        mSataCount,
    )
}

fun MotherboardSlots.toEmbeddable(): MotherboardSlotsEmbeddable = MotherboardSlotsEmbeddable(
    pciExpressX16Count,
    pciExpressX8Count,
    pciExpressX4Count,
    pciExpressX1Count,
    pciCount,
    m2Count,
    mSataCount,
)
