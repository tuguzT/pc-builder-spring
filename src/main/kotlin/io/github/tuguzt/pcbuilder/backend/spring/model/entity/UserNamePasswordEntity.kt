package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "user_name_password")
@PrimaryKeyJoinColumn(name = "user_id")
class UserNamePasswordEntity(
    override val id: String = randomNanoId(),
    override val role: UserRole,
    override val username: String,
    override val email: String?,
    override val imageUri: String?,
    override val password: String,
) : UserEntity(id, role, username, email, imageUri), UserNamePassword {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserNamePasswordEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
