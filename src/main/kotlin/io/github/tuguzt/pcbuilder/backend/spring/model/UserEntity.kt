package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
@Schema(description = "Данные пользователя системы")
@Serializable
open class UserEntity(
    @Id
    @Schema(description = "Идентификатор пользователя системы")
    override val id: String = randomNanoId(),

    @Schema(description = "Роль пользователя системы")
    override val role: UserRole,

    @Column(unique = true)
    @Schema(description = "Адрес электронной почты пользователя системы")
    override val email: String?,

    @SerialName("image_uri")
    @Schema(description = "Ссылка на аватар пользователя системы")
    override val imageUri: String?,
) : User {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
