package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardMemorySlotCount
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.toUInt
import javax.persistence.Embeddable

@Embeddable
data class MotherboardMemorySlotCountEmbeddable(private val memorySlotCount: UInt) {
    fun toMotherboardMemorySlotCount(): MotherboardMemorySlotCount =
        MotherboardMemorySlotCount(count = memorySlotCount)
}

fun MotherboardMemorySlotCount.toEmbeddable(): MotherboardMemorySlotCountEmbeddable =
    MotherboardMemorySlotCountEmbeddable(memorySlotCount = toUInt())
