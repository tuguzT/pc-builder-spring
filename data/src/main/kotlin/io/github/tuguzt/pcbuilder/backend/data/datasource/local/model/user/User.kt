package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import io.github.tuguzt.pcbuilder.domain.model.user.User as IUser

/**
 * Data Access Object of the [user][IUser].
 */
internal class User(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, User>(Users)

    var username by Users.username
    var role by Users.role
    var email by Users.email
    var imageUri by Users.imageUri
}
