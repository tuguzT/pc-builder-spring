package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/**
 * Represents an asset from the [image set][PartResult.Item.imageSets] item.
 */
@Serializable
@Schema(description = "Данные об изображении изображение")
data class Asset(
    @Schema(description = "Ссылка на изображение")
    val url: String,

    @Schema(description = "Тип изображения")
    val mimetype: String,

    @Schema(description = "Метаданные изображения")
    val metadata: Unit? = null,
)
