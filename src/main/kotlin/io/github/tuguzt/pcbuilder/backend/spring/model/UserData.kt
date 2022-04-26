package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Schema(description = "Данные пользователя системы")
data class UserData(
    @Schema(description = "Идентификатор пользователя системы")
    override val id: String,

    @Schema(description = "Адрес электронной почты пользователя системы")
    override val email: String?,

    @SerialName("image_uri")
    @Schema(description = "Ссылка на аватар пользователя системы")
    override val imageUri: String?,

    @Schema(description = "Роль пользователя системы")
    override val role: UserRole,

    @Schema(description = "Имя пользователя системы")
    override val username: String,
) : User {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as UserData
        return this.id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
