package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/**
 * Represents the source item from the [Attribution.sources].
 */
@Serializable
@Schema(description = "Источник данных о компоненте")
data class Source(
    @Schema(description = "Идентификатор компонента")
    val uid: String? = null,
    @Schema(description = "Название компонента")
    val name: String,
)
