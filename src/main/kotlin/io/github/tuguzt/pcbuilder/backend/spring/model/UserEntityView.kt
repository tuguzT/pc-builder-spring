package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserView(
    override val id: String,
    override val email: String?,
    @SerialName("image_uri") override val imageUri: String?,
    override val role: UserRole,
) : User

fun User.toView() = UserView(id, email, imageUri, role)
