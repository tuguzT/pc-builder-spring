package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
@Schema(description = "Данные для входа пользователя по имени и паролю")
data class UserCredentialsData(
    @Schema(description = "Имя пользователя")
    override val username: String,

    @Schema(description = "Пароль пользователя")
    override val password: String,
) : UserCredentials
