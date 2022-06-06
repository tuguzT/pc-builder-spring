package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.model.component.Manufacturer
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserNamePasswordData.toEntity() = UserNamePasswordEntity(
    id = id.toString(),
    role = role,
    username = username,
    email = email,
    imageUri = imageUri,
    password = password,
)

fun GoogleUserData.toEntity() = GoogleUserEntity(
    id = id.toString(),
    role = role,
    username = username,
    email = email,
    imageUri = imageUri,
    googleId = googleId,
)

fun UserData.toEntity() = UserEntity(
    id = id.toString(),
    role = role,
    username = username,
    email = email,
    imageUri = imageUri,
)

fun Manufacturer.toEntity() = ManufacturerEntity(id.toString(), name, description)

fun PolymorphicComponent.toEntity(favorites: Set<UserEntity>) = ComponentEntity(
    id = id.toString(),
    name = name,
    description = description,
    weight = weight.toEmbeddable(),
    size = size.toEmbeddable(),
    manufacturer = manufacturer.toEntity(),
    imageUri = imageUri,
    favorites = favorites,
)

fun MotherboardFormFactor.toEntity() = MotherboardFormFactorEntity(id = this)

fun CaseData.toEntity(favorites: Set<UserEntity>) = CaseEntity(
    id = id.toString(),
    name = name,
    description = description,
    weight = weight.toEmbeddable(),
    size = size.toEmbeddable(),
    manufacturer = manufacturer.toEntity(),
    imageUri = imageUri,
    favorites = favorites,
    type = caseType.toEmbeddable(),
    powerSupply = powerSupply?.toEmbeddable(),
    powerSupplyShroud = powerSupplyShroud,
    sidePanelWindow = sidePanelWindow,
    motherboardFormFactorEntities = motherboardFormFactors.map { it.toEntity() },
    driveBays = driveBays.toEmbeddable(),
    expansionSlots = expansionSlots.toEmbeddable(),
)
