package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserView(
    override val id: String,
    override val email: String?,
    override val imageUri: String?,
    override val role: UserRole,
    override val username: String,
) : User

fun UserEntity.toView() = UserView(id, email, imageUri, role, username)
