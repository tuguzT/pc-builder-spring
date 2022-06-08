package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component

import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.github.tuguzt.pcbuilder.domain.model.component.asMeasure
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import javax.persistence.Embeddable

@Embeddable
data class WeightEmbeddable(private val weight: Double) {
    fun toWeight() = Weight(weight * grams)
}

fun Weight.toEmbeddable() = WeightEmbeddable(weight = asMeasure() `in` grams)
