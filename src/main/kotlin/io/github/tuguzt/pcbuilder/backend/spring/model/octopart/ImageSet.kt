package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an item from the list of [image sets][PartResult.Item.imageSets].
 */
@Serializable
@Schema(description = "Данные изображения компонента в Octopart API")
data class ImageSet(
    @SerialName("swatch_image")
    @Schema(description = "Крошечное изображение")
    val swatchImage: Asset? = null,

    @SerialName("small_image")
    @Schema(description = "Маленькое изображение")
    val smallImage: Asset? = null,

    @SerialName("medium_image")
    @Schema(description = "Среднее изображение")
    val mediumImage: Asset? = null,

    @SerialName("large_image")
    @Schema(description = "Большое изображение")
    val largeImage: Asset? = null,

    @Schema(description = "Атрибуты данных изображения")
    val attribution: Attribution,

    @SerialName("credit_string")
    val credits: String? = null,

    @SerialName("credit_url")
    val creditUrl: String? = null,
)
