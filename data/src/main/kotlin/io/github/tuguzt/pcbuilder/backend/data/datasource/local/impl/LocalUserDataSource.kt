package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.domain.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * [User data source][UserDataSource] implementation which uses local database.
 */
internal class LocalUserDataSource(
    private val database: Database,
    private val context: CoroutineDispatcher = Dispatchers.IO,
) : UserDataSource {

    override suspend fun readAll(): Result<List<UserData>, Nothing?> = runCatching {
        val users = newSuspendedTransaction(context, database) {
            UserEntity.all().toList()
        }
        users.map { it.toData() }
    }.toResult()

    override suspend fun readById(id: NanoId): Result<UserData?, Nothing?> = runCatching {
        val user = newSuspendedTransaction(context, database) {
            UserEntity.findById(id = "$id")
        }
        user?.toData()
    }.toResult()

    override suspend fun save(item: UserData): Result<UserData, Nothing?> = runCatching {
        val user = newSuspendedTransaction(context, database) {
            when (val user = UserEntity.findById(id = "${item.id}")) {
                null -> item.newEntity()
                else -> item.saveIntoEntity(user)
            }
        }
        user.toData()
    }.toResult()

    override suspend fun delete(item: UserData): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            UserEntity.findById(id = "${item.id}")?.delete()
        }
        Unit
    }.toResult()

    override suspend fun clear(): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            Users.deleteAll()
        }
        Unit
    }.toResult()

    override suspend fun readByUsername(username: String): Result<UserData?, Nothing?> = runCatching {
        val user = newSuspendedTransaction(context, database) {
            UserEntity.find { Users.username eq username }.limit(1).firstOrNull()
        }
        user?.toData()
    }.toResult()

    private fun UserEntity.toData(): UserData = UserData(id.value.let(::NanoId), role, username, email, imageUri)
}
