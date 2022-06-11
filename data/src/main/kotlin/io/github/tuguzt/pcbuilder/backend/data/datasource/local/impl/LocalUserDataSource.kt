package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.User
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.domain.toResult
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * [User data source][UserDataSource] implementation which uses local database.
 */
internal class LocalUserDataSource(private val database: Database) : UserDataSource {
    override suspend fun readAll(): Result<List<UserData>, Nothing?> = runCatching {
        val users = newSuspendedTransaction(Dispatchers.IO, database) {
            User.all().toList()
        }
        users.map { it.toData() }
    }.toResult()

    override suspend fun readById(id: NanoId): Result<UserData?, Nothing?> = runCatching {
        val user = newSuspendedTransaction(Dispatchers.IO, database) {
            User.findById(id = "$id")
        }
        user?.toData()
    }.toResult()

    override suspend fun save(item: UserData): Result<UserData, Nothing?> = runCatching {
        val user = newSuspendedTransaction(Dispatchers.IO, database) {
            when (val user = User.findById(id = "${item.id}")) {
                null -> User.new(id = "${item.id}") {
                    username = item.username
                    role = item.role
                    email = item.email
                    imageUri = item.imageUri
                }
                else -> {
                    user.username = item.username
                    user.role = item.role
                    user.email = item.email
                    user.imageUri = item.imageUri
                    user
                }
            }
        }
        user.toData()
    }.toResult()

    override suspend fun delete(item: UserData): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            User.findById(id = "${item.id}")?.delete()
        }
        Unit
    }.toResult()

    override suspend fun clear(): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(Dispatchers.IO, database) {
            Users.deleteAll()
        }
        Unit
    }.toResult()

    override suspend fun readByUsername(username: String): Result<UserData?, Nothing?> = runCatching {
        val user = newSuspendedTransaction(Dispatchers.IO, database) {
            User.find { Users.username eq username }.limit(1).firstOrNull()
        }
        user?.toData()
    }.toResult()

    private fun User.toData(): UserData = UserData(NanoId(id.value), role, username, email, imageUri)
}
