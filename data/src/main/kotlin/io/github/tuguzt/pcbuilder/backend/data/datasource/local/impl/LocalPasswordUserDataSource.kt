package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUser
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUsers
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.User
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.toResult
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * [Password user data source][PasswordUserDataSource] implementation which uses local database.
 */
internal class LocalPasswordUserDataSource(private val database: Database) : PasswordUserDataSource {
    override suspend fun readByUsername(username: String): Result<PasswordUserData?, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            val user = User.find { Users.username eq username }.limit(1).firstOrNull()
            user?.let { PasswordUser.findById(it.id.value) }?.toData()
        }
    }.toResult()

    override suspend fun readAll(): Result<List<PasswordUserData>, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            val users = PasswordUser.all().toList()
            users.map { it.toData() }
        }
    }.toResult()

    override suspend fun readById(id: NanoId): Result<PasswordUserData?, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            val user = PasswordUser.findById(id = "$id")
            user?.toData()
        }
    }.toResult()

    override suspend fun save(item: PasswordUserData): Result<PasswordUserData, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            val user = when (val userById = PasswordUser.findById(id = "${item.id}")) {
                null -> {
                    val parent = User.new(id = "${item.id}") {
                        username = item.username
                        role = item.role
                        email = item.email
                        imageUri = item.imageUri
                    }
                    PasswordUser.new(id = "${item.id}") {
                        user = parent
                        passwordHash = item.password
                    }
                }
                else -> {
                    userById.user.username = item.username
                    userById.user.role = item.role
                    userById.user.email = item.email
                    userById.user.imageUri = item.imageUri
                    userById.passwordHash = item.password
                    userById
                }
            }
            user.toData()
        }
    }.toResult()

    override suspend fun delete(item: PasswordUserData): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            PasswordUser.findById(id = "${item.id}")?.delete()
        }
        Unit
    }.toResult()

    override suspend fun clear(): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            PasswordUsers.deleteAll()
        }
        Unit
    }.toResult()

    private fun PasswordUser.toData(): PasswordUserData = PasswordUserData(
        id = user.id.value.let(::NanoId),
        role = user.role,
        username = user.username,
        email = user.email,
        imageUri = user.imageUri,
        password = passwordHash,
    )
}
