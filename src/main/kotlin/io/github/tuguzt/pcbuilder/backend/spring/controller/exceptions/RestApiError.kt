package io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class RestApiError(val message: String)
