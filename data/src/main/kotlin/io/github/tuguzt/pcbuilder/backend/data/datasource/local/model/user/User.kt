package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Data Access Object of the [user][UserData].
 */
internal class User(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, User>(Users)

    var username by Users.username
    var role by Users.role
    var email by Users.email
    var imageUri by Users.imageUri
}
