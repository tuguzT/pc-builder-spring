package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

/**
 * Serializable entity class for [UserNamePassword].
 */
@Entity
@Table(name = "user_name_password")
@PrimaryKeyJoinColumn(name = "user_id")
@Serializable
class UserNamePasswordEntity(
    @Transient
    override val id: String = randomNanoId(),

    @Transient
    override val email: String? = null,

    @Transient
    override val imageUri: String? = null,

    @Transient
    override val role: UserRole = UserRole.User,

    @Column(unique = true)
    override val username: String,

    override val password: String,
) : UserEntity(id, role, email, imageUri), UserNamePassword {

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserNamePasswordEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
