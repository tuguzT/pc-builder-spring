package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import kotlinx.serialization.Serializable
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
    @OneToOne(cascade = [CascadeType.ALL])
    @MapsId
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    override val password: String,
) : User by user, UserNamePassword {
    @Id
    @Column(name = "user_id")
    override val id = user.id

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserNamePasswordEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
