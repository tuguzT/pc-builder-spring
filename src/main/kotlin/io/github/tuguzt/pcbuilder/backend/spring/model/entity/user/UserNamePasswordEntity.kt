package io.github.tuguzt.pcbuilder.backend.spring.model.entity.user

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.springframework.data.util.ProxyUtils
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_name_password")
class UserNamePasswordEntity(
    id: String,
    role: UserRole,
    username: String,
    email: String?,
    imageUri: String?,
    favoriteComponents: MutableSet<ComponentEntity>,
    override val password: String,
) : UserEntity(id, role, username, email, imageUri, favoriteComponents), UserCredentials {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserNamePasswordEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
