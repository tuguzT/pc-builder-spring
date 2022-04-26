package io.github.tuguzt.pcbuilder.backend.spring.model

fun UserOAuth2Data.toUser() = UserData(id, email, imageUri, role, username)

fun UserNamePasswordData.toUser() = UserData(id, email, imageUri, role, username)
