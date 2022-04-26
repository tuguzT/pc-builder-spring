package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.backend.spring.model.UserData
import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Data

fun UserEntity.toData() = UserData(id, email, imageUri, role, username)

fun UserNamePasswordEntity.toData() = UserNamePasswordData(id, email, imageUri, role, username, password)

fun UserOAuth2Entity.toData() = UserOAuth2Data(id, email, imageUri, role, username, accessToken)
