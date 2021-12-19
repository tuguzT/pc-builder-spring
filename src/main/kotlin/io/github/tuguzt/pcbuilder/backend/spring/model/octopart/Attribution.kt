package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an attribution from the [ImageSet.attribution].
 */
@Serializable
data class Attribution(
    val sources: List<Source>,
    @SerialName("first_acquired") val firstAcquired: String? = null,
)
