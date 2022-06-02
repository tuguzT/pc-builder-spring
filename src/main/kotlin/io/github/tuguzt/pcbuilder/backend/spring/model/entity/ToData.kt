package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.backend.spring.model.GoogleUserData
import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserEntity.toData() = UserData(id, role, username, email, imageUri)

fun UserNamePasswordEntity.toData() = UserNamePasswordData(id, email, imageUri, role, username, password)

fun GoogleUserEntity.toData() = GoogleUserData(id, email, imageUri, role, username, googleId)

fun ComponentEntity.toData() = ComponentData(id, name, description, weight, size, manufacturer.toData())

fun ManufacturerEntity.toData() = ManufacturerData(id, name, description)

fun CaseEntity.toData() = CaseData(
    id = id,
    name = name,
    description = description,
    weight = weight,
    size = size,
    manufacturer = manufacturer.toData(),
    driveBays = driveBays,
    expansionSlots = expansionSlots,
    motherboardFormFactors = motherboardFormFactors,
    powerSupply = powerSupply,
    powerSupplyShroud = powerSupplyShroud,
    sidePanelWindow = sidePanelWindow,
    type = type,
)
