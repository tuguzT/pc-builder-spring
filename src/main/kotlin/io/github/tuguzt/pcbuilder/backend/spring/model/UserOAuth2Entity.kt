package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserOAuth2
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "user_oauth2")
class UserOAuth2Entity(
    @OneToOne(cascade = [CascadeType.ALL])
    @MapsId
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(unique = true)
    override val accessToken: String,
) : User by user, UserOAuth2 {
    @Id
    @Column(name = "user_id")
    override val id = user.id

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserOAuth2Entity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
