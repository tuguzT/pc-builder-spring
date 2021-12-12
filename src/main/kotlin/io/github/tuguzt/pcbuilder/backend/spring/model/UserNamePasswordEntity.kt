package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.Serializable
import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Serializable entity class for [UserNamePassword].
 */
@Entity
@Table(name = "user_name_password")
@Serializable
class UserNamePasswordEntity(
    @Id
    override val id: String = randomNanoId(),

    @Column(unique = true)
    override val username: String,

    override val role: UserRole,

    override val password: String,

    @Column(unique = true)
    override val email: String?,

    override val imageUri: String?,
) : UserNamePassword {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserNamePasswordEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
