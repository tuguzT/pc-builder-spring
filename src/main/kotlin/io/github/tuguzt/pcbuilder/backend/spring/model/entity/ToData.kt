package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Data
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserEntity.toData() = UserData(id, role, username, email, imageUri)

fun UserNamePasswordEntity.toData() = UserNamePasswordData(id, email, imageUri, role, username, password)

fun UserOAuth2Entity.toData() = UserOAuth2Data(id, email, imageUri, role, username, accessToken)

fun ComponentEntity.toData() = ComponentData(id, name, description, weight, size)
