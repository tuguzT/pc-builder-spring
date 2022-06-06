package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard

import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardMemoryAmount
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.asMeasure
import io.nacular.measured.units.BinarySize.Companion.gigabytes
import io.nacular.measured.units.times
import javax.persistence.Embeddable

@Embeddable
data class MotherboardMemoryAmountEmbeddable(private val memoryAmount: Double) {
    fun toMotherboardMemoryAmount(): MotherboardMemoryAmount =
        MotherboardMemoryAmount(amount = memoryAmount * gigabytes)
}

fun MotherboardMemoryAmount.toEmbeddable(): MotherboardMemoryAmountEmbeddable =
    MotherboardMemoryAmountEmbeddable(memoryAmount = asMeasure() `in` gigabytes)
