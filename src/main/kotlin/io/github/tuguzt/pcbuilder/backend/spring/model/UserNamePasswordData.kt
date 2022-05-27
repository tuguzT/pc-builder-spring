package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserNamePasswordData(
    override val id: String = randomNanoId(),
    override val email: String?,
    override val imageUri: String?,
    override val role: UserRole,
    override val username: String,
    override val password: String,
) : User, UserCredentials {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as UserNamePasswordData
        return this.id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
