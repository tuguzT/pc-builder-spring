package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user

import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents SQL table of [users][PasswordUserData] with passwords.
 */
internal object PasswordUsers : IdTable<String>() {
    override val id = reference(name = "user_id", foreign = Users, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(id)

    val passwordHash = varchar(name = "password_hash", length = 98)
}
