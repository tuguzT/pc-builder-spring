package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.UserToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTokenData(@SerialName("access_token") override val accessToken: String) : UserToken
