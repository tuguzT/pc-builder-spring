package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/**
 * Represents the [result][SearchResponse] of searching query by [OctopartAPI.searchQuery].
 */
@Serializable
@Schema(description = "Данные компонента в Octopart API в более удобном формате")
data class SearchResult(private val partResult: PartResult) : Identifiable<String> {
    @get:Schema(description = "Идентификатор компонента")
    override val id get() = partResult.item.uid

    @get:Schema(description = "Текстовое описание компонента")
    val description get() = partResult.item.description

    @get:Schema(description = "Изображения компонента")
    val images get() = partResult.item.imageSets
}

/**
 * Converts [SearchResponse] into list of [SearchResult].
 */
fun SearchResponse.toResults() = results.map { SearchResult(it) }
