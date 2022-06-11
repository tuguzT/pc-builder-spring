package io.github.tuguzt.pcbuilder.backend.data.repository

import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository

/**
 * Simple implementation of domain [user repository][UserRepository].
 */
internal class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository<Nothing?> {
    override suspend fun clear(): Result<Unit, Nothing?> = dataSource.clear()

    override suspend fun delete(item: UserData): Result<Unit, Nothing?> = dataSource.delete(item)

    override suspend fun readAll(): Result<List<UserData>, Nothing?> = dataSource.readAll()

    override suspend fun readById(id: NanoId): Result<UserData?, Nothing?> = dataSource.readById(id)

    override suspend fun readByUsername(username: String): Result<UserData?, Nothing?> =
        dataSource.readByUsername(username)

    override suspend fun save(item: UserData): Result<UserData, Nothing?> = dataSource.save(item)
}
