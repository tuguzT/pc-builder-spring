package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUserEntity
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

internal fun UserData.newEntity(): UserEntity = newUserEntity()

internal fun UserData.saveIntoEntity(entity: UserEntity): UserEntity = saveIntoUserEntity(entity)

internal fun PasswordUserData.newEntity(): PasswordUserEntity {
    val parent = newUserEntity()
    return PasswordUserEntity.new(id) {
        user = parent
        passwordHash = this@newEntity.password
    }
}

internal fun PasswordUserData.saveIntoEntity(entity: PasswordUserEntity): PasswordUserEntity {
    saveIntoUserEntity(entity.user)
    return entity.apply {
        passwordHash = this@saveIntoEntity.password
    }
}

internal fun ManufacturerData.newEntity(): ManufacturerEntity = ManufacturerEntity.new(id) {
    name = this@newEntity.name
    description = this@newEntity.description
}

internal fun ManufacturerData.saveIntoEntity(entity: ManufacturerEntity): ManufacturerEntity {
    require(id == entity.id.value) { "IDs of data and entity must be the same" }

    return entity.apply {
        name = this@saveIntoEntity.name
        description = this@saveIntoEntity.description
    }
}

// --- IMPLEMENTATION FOR INTERFACES ---

private fun User.newUserEntity(): UserEntity = UserEntity.new(id) {
    username = this@newUserEntity.username
    role = this@newUserEntity.role
    email = this@newUserEntity.email
    imageUri = this@newUserEntity.imageUri
}

private fun User.saveIntoUserEntity(entity: UserEntity): UserEntity {
    require(id == entity.id.value) { "IDs of data and entity must be the same" }

    return entity.apply {
        username = this@saveIntoUserEntity.username
        role = this@saveIntoUserEntity.role
        email = this@saveIntoUserEntity.email
        imageUri = this@saveIntoUserEntity.imageUri
    }
}
