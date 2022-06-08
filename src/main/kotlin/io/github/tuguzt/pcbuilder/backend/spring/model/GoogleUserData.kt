package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole

data class GoogleUserData(
    override val id: NanoId = randomNanoId(),
    override val email: String?,
    override val imageUri: String?,
    override val role: UserRole,
    override val username: String,
    val googleId: String,
) : User
