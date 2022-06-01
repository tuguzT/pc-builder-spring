package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CaseDriveBays
import javax.persistence.Embeddable

@Embeddable
data class CaseDriveBaysEmbeddable(
    private val external5_25_count: UInt,
    private val external3_5_count: UInt,
    private val internal3_5_count: UInt,
    private val internal2_5_count: UInt,
) {
    fun toCaseDriveBays() = CaseDriveBays(
        external5_25_count,
        external3_5_count,
        internal3_5_count,
        internal2_5_count,
    )
}

fun CaseDriveBays.toEmbeddable() = CaseDriveBaysEmbeddable(
    external5_25_count,
    external3_5_count,
    internal3_5_count,
    internal2_5_count,
)
