package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component

import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.times
import javax.persistence.Embeddable

@Embeddable
data class SizeEmbeddable(
    private val length: Double,
    private val width: Double,
    private val height: Double,
) {
    fun toSize() = Size(
        length = length * meters,
        width = width * meters,
        height = height * meters,
    )
}

fun Size.toEmbeddable() = SizeEmbeddable(
    length = length `in` meters,
    width = width `in` meters,
    height = height `in` meters,
)
