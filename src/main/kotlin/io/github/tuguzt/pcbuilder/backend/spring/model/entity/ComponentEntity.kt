package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "component")
@Schema(description = "Данные компонента ПК системы")
class ComponentEntity(
    @Id
    @Schema(description = "Идентификатор компонента")
    override val id: String = randomNanoId(),

    @Schema(description = "Название компонента")
    override val name: String,

    @Schema(description = "Описание компонента")
    override val description: String,

    @Column(name = "weight")
    @Schema(description = "Вес компонента в граммах")
    private val weightInGrams: Double,

    @Column(name = "length")
    @Schema(description = "Длина компонента в метрах")
    private val lengthInMeters: Double,

    @Column(name = "width")
    @Schema(description = "Ширина компонента в метрах")
    private val widthInMeters: Double,

    @Column(name = "height")
    @Schema(description = "Высота компонента в метрах")
    private val heightInMeters: Double,
) : Component {

    @delegate:Transient
    override val weight: Measure<Mass> by lazy { weightInGrams * grams }

    @delegate:Transient
    override val size: Size by lazy {
        Size(
            length = lengthInMeters * meters,
            width = widthInMeters * meters,
            height = heightInMeters * meters,
        )
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as ComponentEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
