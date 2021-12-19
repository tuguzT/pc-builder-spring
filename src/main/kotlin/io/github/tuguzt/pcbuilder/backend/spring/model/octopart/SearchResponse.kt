package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import kotlinx.serialization.Serializable

/**
 * Response from the [OctopartAPI.searchQuery].
 */
@Serializable
data class SearchResponse(
    val hits: Int? = null,
    val results: List<PartResult>,
)
