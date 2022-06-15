package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Database entity of the [user][UserData].
 */
internal class UserEntity(id: EntityID<NanoId>) : Entity<NanoId>(id) {
    companion object : EntityClass<NanoId, UserEntity>(Users)

    var username by Users.username
    var role by Users.role
    var email by Users.email
    var imageUri by Users.imageUri
}
