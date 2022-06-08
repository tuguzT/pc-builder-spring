package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseExpansionSlots
import javax.persistence.Embeddable

@Embeddable
data class CaseExpansionSlotsEmbeddable(
    private val fullHeightCount: UInt,
    private val halfHeightCount: UInt,
) {
    fun toExpansionSlots() = CaseExpansionSlots(fullHeightCount, halfHeightCount)
}

fun CaseExpansionSlots.toEmbeddable() = CaseExpansionSlotsEmbeddable(fullHeightCount, halfHeightCount)
