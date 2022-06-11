package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.jetbrains.exposed.dao.id.IdTable

/**
 * Represents SQL table of [users][User].
 */
internal object Users : IdTable<String>() {
    override val id = char(name = "id", length = 21).entityId()
    override val primaryKey = PrimaryKey(id)

    val username = varchar(name = "username", length = 32)
    val role = enumerationByName<UserRole>(name = "role", length = 16)
    val email = varchar(name = "email", length = 320).nullable()
    val imageUri = text(name = "imageUri").nullable()
}
