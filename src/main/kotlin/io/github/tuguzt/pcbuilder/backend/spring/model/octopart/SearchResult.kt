package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import kotlinx.serialization.Serializable

/**
 * Represents the [result][SearchResponse] of searching query by [OctopartAPI.searchQuery].
 */
@Serializable
data class SearchResult(private val partResult: PartResult) : Identifiable<String> {
    override val id get() = partResult.item.uid
    val description get() = partResult.item.description
    val images get() = partResult.item.imageSets
}

/**
 * Converts [SearchResponse] into list of [SearchResult].
 */
fun SearchResponse.toResults() = results.map { SearchResult(it) }
