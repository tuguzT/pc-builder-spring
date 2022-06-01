package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserNamePasswordData.toEntity() = UserNamePasswordEntity(id, role, username, email, imageUri, password)

fun GoogleUserData.toEntity() = GoogleUserEntity(id, role, username, email, imageUri, googleId)

fun UserData.toEntity() = UserEntity(id, role, username, email, imageUri)

fun ManufacturerData.toEntity() = ManufacturerEntity(id, name, description)

fun ComponentData.toEntity() = ComponentEntity(
    id = id,
    name = name,
    description = description,
    weight = weight.toEmbeddable(),
    size = size.toEmbeddable(),
    manufacturer = manufacturer.toEntity(),
)
