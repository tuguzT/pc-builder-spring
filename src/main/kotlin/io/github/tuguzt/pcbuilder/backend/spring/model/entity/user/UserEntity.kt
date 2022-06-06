package io.github.tuguzt.pcbuilder.backend.spring.model.entity.user

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
open class UserEntity(
    @Id override val id: String,
    open val role: UserRole,
    open val username: String,
    open val email: String?,
    open val imageUri: String?,
) : Identifiable<String> {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as UserEntity
        return this.id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
