package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data about the part from the [SearchResponse.results].
 */
@Serializable
@Schema(description = "Данные компонента в Octopart API")
data class PartResult(val item: Item) {
    @Serializable
    @Schema(description = "Внутренний объект данных компонента в Octopart API")
    data class Item(
        @Schema(description = "Идентификатор компонента")
        val uid: String,

        @SerialName("short_description")
        @Schema(description = "Текстовое описание компонента")
        val description: String,

        @SerialName("imagesets")
        @Schema(description = "Набор изображений компонента")
        val imageSets: List<ImageSet>,
    )
}
