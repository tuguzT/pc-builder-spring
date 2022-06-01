package io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases

import io.github.tuguzt.pcbuilder.domain.model.component.cases.CasePowerSupply
import io.github.tuguzt.pcbuilder.domain.model.units.watt
import io.nacular.measured.units.times
import javax.persistence.Embeddable

@Embeddable
data class CasePowerSupplyEmbeddable(private val power: Double) {
    fun toPowerSupply() = CasePowerSupply(power = power * watt)
}

fun CasePowerSupply.toEmbeddable() = CasePowerSupplyEmbeddable(power = power `in` watt)
