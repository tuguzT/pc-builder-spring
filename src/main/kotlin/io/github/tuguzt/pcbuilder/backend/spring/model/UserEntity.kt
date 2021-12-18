package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.springframework.data.util.ProxyUtils
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "\"user\"")
@Serializable
class UserEntity(
    @Id
    override val id: String = randomNanoId(),

    override val role: UserRole,

    @Column(unique = true)
    override val email: String?,

    @SerialName("image_uri")
    override val imageUri: String?,
) : User {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserEntity
        return this.id == other.id
    }

    override fun hashCode() = javaClass.hashCode()
}
