package io.github.tuguzt.pcbuilder.backend.spring.model.octopart

import kotlinx.serialization.Serializable

/**
 * Represents the source item from the [Attribution.sources].
 */
@Serializable
data class Source(
    val uid: String? = null,
    val name: String,
)
