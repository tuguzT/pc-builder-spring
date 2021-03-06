package io.github.tuguzt.pcbuilder.backend.spring.model.entity.user

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_google")
class GoogleUserEntity(
    id: String,
    role: UserRole,
    username: String,
    email: String?,
    imageUri: String?,
    favoriteComponents: MutableSet<ComponentEntity>,
    @Column(unique = true) val googleId: String,
) : UserEntity(id, role, username, email, imageUri, favoriteComponents) {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as GoogleUserEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
