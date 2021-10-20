package io.github.tuguzt.pcbuilder.backend.spring.model

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.Size
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.times
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Serializable data class for [Component].
 */
@Serializable
data class ComponentData(
    override val id: String = NanoIdUtils.randomNanoId(),
    override val name: String,
    override val description: String,
    @SerialName("weight") private val weightInGrams: Double,
    @SerialName("length") private val lengthInMeters: Double,
    @SerialName("width") private val widthInMeters: Double,
    @SerialName("height") private val heightInMeters: Double,
) : Component {
    @Transient
    override val weight = weightInGrams * grams
    @Transient
    override val size = Size(
        length = lengthInMeters * meters,
        width = widthInMeters * meters,
        height = heightInMeters * meters,
    )
}
