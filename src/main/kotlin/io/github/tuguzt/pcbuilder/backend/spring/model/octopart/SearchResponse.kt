package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/**
 * Response from the [OctopartAPI.searchQuery].
 */
@Serializable
@Schema(description = "Результат запроса поиска в Octopart API")
data class SearchResponse(
    val hits: Int? = null,
    @Schema(description = "Данные найденных компонентов")
    val results: List<PartResult> = listOf(),
)
