package io.github.tuguzt.pcbuilder.backend.data.model

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole

data class PasswordUserData(
    override val id: NanoId,
    override val role: UserRole,
    override val username: String,
    override val email: String?,
    override val imageUri: String?,
    override val password: String,
) : User, UserCredentials
