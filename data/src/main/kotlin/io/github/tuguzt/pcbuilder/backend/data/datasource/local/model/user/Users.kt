package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.jetbrains.exposed.dao.id.IdTable

/**
 * Represents SQL table of [users][UserData].
 */
internal object Users : IdTable<String>() {
    override val id = char(name = "id", length = 21).entityId()
    override val primaryKey = PrimaryKey(id)

    val username = varchar(name = "username", length = 32).uniqueIndex()
    val role = enumerationByName<UserRole>(name = "role", length = 16)
    val email = varchar(name = "email", length = 320).nullable()
    val imageUri = text(name = "image_uri").nullable()
}
