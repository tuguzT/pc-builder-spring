package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.UserOAuth2
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.SerialName
import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_oauth2")
class UserOAuth2Entity(
    @Id
    override val id: String = randomNanoId(),

    @Column(unique = true)
    override val accessToken: String,

    override val role: UserRole,

    @Column(unique = true)
    override val email: String?,

    @SerialName("image_uri")
    override val imageUri: String?,
) : UserOAuth2 {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserOAuth2Entity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
