package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUserEntity
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.PasswordUsers
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.user.Users
import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * [Password user data source][PasswordUserDataSource] implementation which uses local database.
 */
internal class LocalPasswordUserDataSource(
    private val database: Database,
    private val context: CoroutineDispatcher = Dispatchers.IO,
) : PasswordUserDataSource {

    override suspend fun readByUsername(username: String): Result<PasswordUserData?, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            val user = UserEntity.find { Users.username eq username }.limit(1).firstOrNull()
            user?.let { PasswordUserEntity.findById(it.id.value) }?.toData()
        }
    }.toResult()

    override suspend fun readAll(): Result<List<PasswordUserData>, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            val users = PasswordUserEntity.all().toList()
            users.map { it.toData() }
        }
    }.toResult()

    override suspend fun readById(id: NanoId): Result<PasswordUserData?, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            val user = PasswordUserEntity.findById(id = "$id")
            user?.toData()
        }
    }.toResult()

    override suspend fun save(item: PasswordUserData): Result<PasswordUserData, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            val user = when (val user = PasswordUserEntity.findById(id = "${item.id}")) {
                null -> item.newEntity()
                else -> item.saveIntoEntity(user)
            }
            user.toData()
        }
    }.toResult()

    override suspend fun delete(item: PasswordUserData): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            PasswordUserEntity.findById(id = "${item.id}")?.delete()
        }
        Unit
    }.toResult()

    override suspend fun clear(): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            PasswordUsers.deleteAll()
        }
        Unit
    }.toResult()

    private fun PasswordUserEntity.toData(): PasswordUserData = PasswordUserData(
        id = user.id.value.let(::NanoId),
        role = user.role,
        username = user.username,
        email = user.email,
        imageUri = user.imageUri,
        password = passwordHash,
    )
}
