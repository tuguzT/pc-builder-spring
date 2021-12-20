package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an attribution from the [ImageSet.attribution].
 */
@Serializable
@Schema(description = "Атрибуты компонента в Octopart API")
data class Attribution(
    @Schema(description = "Источники данных")
    val sources: List<Source>,

    @SerialName("first_acquired")
    val firstAcquired: String? = null,
)
