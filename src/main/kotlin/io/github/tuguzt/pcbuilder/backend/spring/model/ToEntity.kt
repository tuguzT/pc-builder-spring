package io.github.tuguzt.pcbuilder.backend.spring.model

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.gpu.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.toEmbeddable
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.model.component.Manufacturer
import io.github.tuguzt.pcbuilder.domain.model.component.data.*
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

fun UserNamePasswordData.toEntity(favoriteComponents: Set<ComponentEntity>) = UserNamePasswordEntity(
    id = id.toString(),
    role = role,
    username = username,
    email = email,
    imageUri = imageUri,
    favoriteComponents = favoriteComponents.toMutableSet(),
    password = password,
)

fun GoogleUserData.toEntity(favoriteComponents: Set<ComponentEntity>) = GoogleUserEntity(
    id = id.toString(),
    role = role,
    username = username,
    email = email,
    imageUri = imageUri,
    favoriteComponents = favoriteComponents.toMutableSet(),
    googleId = googleId,
)

fun UserData.toEntity(favoriteComponents: Set<ComponentEntity>) = UserEntity(
    id = id.toString(),
    role = role,
    username = username,
    email = email,
    imageUri = imageUri,
    favoriteComponents = favoriteComponents.toMutableSet(),
)

fun Manufacturer.toEntity() = ManufacturerEntity(id.toString(), name, description)

fun PolymorphicComponent.toEntity(favorites: Set<UserEntity>, manufacturer: ManufacturerEntity? = null) = when (this) {
    is CaseData -> toEntity(favorites, manufacturer)
    is CoolerData -> TODO()
    is CpuData -> TODO()
    is GpuData -> TODO()
    is MemoryData -> TODO()
    is MonitorData -> TODO()
    is MotherboardData -> toEntity(favorites, manufacturer)
    is PsuData -> TODO()
    is StorageData -> TODO()
}

fun MotherboardFormFactor.toEntity() = MotherboardFormFactorEntity(id = this)

fun CaseData.toEntity(favorites: Set<UserEntity>, manufacturer: ManufacturerEntity? = null) = CaseEntity(
    id = id.toString(),
    name = name,
    description = description,
    weight = weight.toEmbeddable(),
    size = size.toEmbeddable(),
    manufacturer = manufacturer ?: this.manufacturer.toEntity(),
    imageUri = imageUri,
    favorites = favorites.toMutableSet(),
    type = caseType.toEmbeddable(),
    powerSupply = powerSupply?.toEmbeddable(),
    powerSupplyShroud = powerSupplyShroud,
    sidePanelWindow = sidePanelWindow,
    motherboardFormFactorEntities = motherboardFormFactors.map { it.toEntity() },
    driveBays = driveBays.toEmbeddable(),
    expansionSlots = expansionSlots.toEmbeddable(),
)

fun MotherboardData.toEntity(favorites: Set<UserEntity>, manufacturer: ManufacturerEntity? = null) = MotherboardEntity(
    id = id.toString(),
    name = name,
    description = description,
    weight = weight.toEmbeddable(),
    size = size.toEmbeddable(),
    manufacturer = manufacturer ?: this.manufacturer.toEntity(),
    imageUri = imageUri,
    favorites = favorites.toMutableSet(),
    formFactorEntity = formFactor.toEntity(),
    memoryAmount = memoryAmount.toEmbeddable(),
    memorySlotCount = memorySlotCount.toEmbeddable(),
    memoryECCType = memoryECCType,
    memoryType = memoryType,
    multiGpuSupport = multiGpuSupport?.toEmbeddable(),
    ports = ports.toEmbeddable(),
    slots = slots.toEmbeddable(),
    usbHeaders = usbHeaders.toEmbeddable(),
    chipset = chipset.toEmbeddable(),
    cpuSocket = cpuSocket.toEmbeddable(),
)
