package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Database entity of the [password user][PasswordUserData].
 */
internal class PasswordUserEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, PasswordUserEntity>(PasswordUsers)

    var passwordHash by PasswordUsers.passwordHash
    var user by UserEntity referencedOn PasswordUsers.id
}
