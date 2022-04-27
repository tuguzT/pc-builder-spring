package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.user.UserOAuth2
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "user_oauth2")
@PrimaryKeyJoinColumn(name = "user_id")
class UserOAuth2Entity(
    override val id: String = randomNanoId(),
    override val role: UserRole,
    override val username: String,
    override val email: String?,
    override val imageUri: String?,
    @Column(unique = true) override val accessToken: String,
) : UserEntity(id, role, username, email, imageUri), UserOAuth2 {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserOAuth2Entity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
