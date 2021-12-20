package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.UserToken
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Schema(description = "Токен доступа пользователя")
data class UserTokenData(@SerialName("access_token") override val accessToken: String) : UserToken
