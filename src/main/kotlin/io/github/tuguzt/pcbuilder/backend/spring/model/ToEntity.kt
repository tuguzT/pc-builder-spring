package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserOAuth2Entity
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams

fun UserNamePasswordData.toEntity() = UserNamePasswordEntity(id, role, username, email, imageUri, password)

fun UserOAuth2Data.toEntity() = UserOAuth2Entity(id, role, username, email, imageUri, accessToken)

fun UserData.toEntity() = UserEntity(id, role, username, email, imageUri)

fun ComponentData.toEntity() = ComponentEntity(
    id = id,
    name = name,
    description = description,
    weightInGrams = weight `in` grams,
    lengthInMeters = size.length `in` meters,
    widthInMeters = size.width `in` meters,
    heightInMeters = size.height `in` meters,
)
