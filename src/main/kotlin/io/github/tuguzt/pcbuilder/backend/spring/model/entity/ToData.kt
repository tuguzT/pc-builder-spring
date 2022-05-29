package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.model.GoogleUserData
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserEntity.toData() = UserData(id, role, username, email, imageUri)

fun UserNamePasswordEntity.toData() = UserNamePasswordData(id, email, imageUri, role, username, password)

fun GoogleUserEntity.toData() = GoogleUserData(id, email, imageUri, role, username, googleId)

fun ComponentEntity.toData() = ComponentData(id, name, description, weight, size)
