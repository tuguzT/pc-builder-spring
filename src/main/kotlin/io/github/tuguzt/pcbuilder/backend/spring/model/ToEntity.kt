package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserOAuth2Entity

fun UserNamePasswordData.toEntity() = UserNamePasswordEntity(id, role, username, email, imageUri, password)

fun UserOAuth2Data.toEntity() = UserOAuth2Entity(id, role, username, email, imageUri, accessToken)

fun UserData.toEntity() = UserEntity(id, role, username, email, imageUri)
