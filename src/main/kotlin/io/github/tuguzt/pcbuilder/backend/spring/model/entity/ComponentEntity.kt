package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.model.component.Weight
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "component")
class ComponentEntity(
    @Id override val id: NanoId,
    override val name: String,
    override val description: String,
    @Column(name = "weight") private val weightInGrams: Double,
    @Column(name = "length") private val lengthInMeters: Double,
    @Column(name = "width") private val widthInMeters: Double,
    @Column(name = "height") private val heightInMeters: Double,
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    override val manufacturer: ManufacturerEntity,
) : Component {

    override val weight: Weight get() = Weight(weightInGrams * grams)

    override val size
        get() = Size(
            length = lengthInMeters * meters,
            width = widthInMeters * meters,
            height = heightInMeters * meters,
        )

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as ComponentEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
