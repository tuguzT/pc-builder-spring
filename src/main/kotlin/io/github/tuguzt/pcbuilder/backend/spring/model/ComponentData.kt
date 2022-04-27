@file:UseSerializers(MeasureSerializer::class, MassSerializer::class)

package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.interactor.serialization.measured.MassSerializer
import io.github.tuguzt.pcbuilder.domain.interactor.serialization.measured.MeasureSerializer
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
@Schema(description = "Данные компонента ПК системы")
data class ComponentData(
    @Schema(description = "Идентификатор компонента")
    override val id: String = randomNanoId(),

    @Schema(description = "Название компонента")
    override val name: String,

    @Schema(description = "Описание компонента")
    override val description: String,

    @Schema(description = "Длина, ширина и высота компонента")
    override val size: Size,

    @Schema(description = "Вес компонента")
    override val weight: Measure<Mass>,
) : Component
