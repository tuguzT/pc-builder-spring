package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun GoogleUserData.toUser() = UserData(id, role, username, email, imageUri)

fun UserNamePasswordData.toUser() = UserData(id, role, username, email, imageUri)
