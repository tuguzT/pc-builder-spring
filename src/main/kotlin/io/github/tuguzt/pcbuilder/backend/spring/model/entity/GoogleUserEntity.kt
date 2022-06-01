package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "user_google")
@PrimaryKeyJoinColumn(name = "user_id")
class GoogleUserEntity(
    override val id: NanoId = randomNanoId(),
    override val role: UserRole,
    override val username: String,
    override val email: String?,
    override val imageUri: String?,
    @Column(unique = true) val googleId: String,
) : UserEntity(id, role, username, email, imageUri) {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as GoogleUserEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
