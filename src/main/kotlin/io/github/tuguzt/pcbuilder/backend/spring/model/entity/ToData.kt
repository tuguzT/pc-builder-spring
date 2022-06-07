package io.github.tuguzt.pcbuilder.backend.spring.model.entity

import io.github.tuguzt.pcbuilder.backend.spring.model.GoogleUserData
import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.build.BuildEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserEntity.toData() = UserData(NanoId(id), role, username, email, imageUri)

fun UserNamePasswordEntity.toData() = UserNamePasswordData(NanoId(id), email, imageUri, role, username, password)

fun GoogleUserEntity.toData() = GoogleUserData(NanoId(id), email, imageUri, role, username, googleId)

fun BuildEntity.toData() = BuildData(
    id = NanoId(id),
    name = name,
    case = case?.toData(user.toData()),
    cooler = null,
    centralProcessingUnit = null,
    graphicsProcessingUnit = listOf(),
    memory = listOf(),
    monitor = listOf(),
    motherboard = motherboard?.toData(user.toData()),
    powerSupplyUnit = null,
    storage = listOf(),
)

fun ComponentEntity.toData(currentUser: User?): PolymorphicComponent = when (this) {
    is CaseEntity -> toData(currentUser)
    is MotherboardEntity -> toData(currentUser)
    else -> error("Cannot cast ComponentEntity to PolymorphicComponent")
}

fun ManufacturerEntity.toData() = ManufacturerData(NanoId(id), name, description)

fun CaseEntity.toData(currentUser: User?) = CaseData(
    id = NanoId(id),
    name = name,
    description = description,
    weight = weight,
    size = size,
    manufacturer = manufacturer.toData(),
    imageUri = imageUri,
    isFavorite = favorites.any { currentUser?.id?.toString() == it.id },
    driveBays = driveBays,
    expansionSlots = expansionSlots,
    motherboardFormFactors = motherboardFormFactors,
    powerSupply = powerSupply,
    powerSupplyShroud = powerSupplyShroud,
    sidePanelWindow = sidePanelWindow,
    caseType = caseType,
)

fun MotherboardEntity.toData(currentUser: User?) = MotherboardData(
    id = NanoId(id),
    name = name,
    description = description,
    weight = weight,
    size = size,
    manufacturer = manufacturer.toData(),
    imageUri = imageUri,
    isFavorite = favorites.any { currentUser?.id?.toString() == it.id },
    formFactor = formFactor,
    chipset = chipset,
    cpuSocket = cpuSocket,
    memoryType = memoryType,
    memoryECCType = memoryECCType,
    memoryAmount = memoryAmount,
    memorySlotCount = memorySlotCount,
    multiGpuSupport = multiGpuSupport,
    slots = slots,
    ports = ports,
    usbHeaders = usbHeaders,
)
