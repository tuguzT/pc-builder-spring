package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

/**
 * Serializable entity class for [Component].
 */
@Entity
@Table(name = "component")
@Serializable
class ComponentEntity(
    @Id
    override val id: String = randomNanoId(),

    override val name: String,

    override val description: String,

    @Column(name = "weight")
    @SerialName("weight")
    private val weightInGrams: Double,

    @Column(name = "length")
    @SerialName("length")
    private val lengthInMeters: Double,

    @Column(name = "width")
    @SerialName("width")
    private val widthInMeters: Double,

    @Column(name = "height")
    @SerialName("height")
    private val heightInMeters: Double,
) : Component {

    @Transient
    @javax.persistence.Transient
    override val weight = weightInGrams * grams

    @Transient
    @javax.persistence.Transient
    override val size = Size(
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

    override fun hashCode() = javaClass.hashCode()
}
