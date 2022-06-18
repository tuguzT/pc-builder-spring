package io.github.tuguzt.pcbuilder.backend.data.repository.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.UserDataSource
import io.github.tuguzt.pcbuilder.backend.data.repository.UserRepository
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

/**
 * Simple implementation of domain [user repository][UserRepository].
 */
internal class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {
    override suspend fun clear(): Result<Unit, Nothing?> = dataSource.clear()

    override suspend fun delete(item: UserData): Result<Unit, Nothing?> = dataSource.delete(item)

    override suspend fun readAll(): Result<List<UserData>, Nothing?> = dataSource.readAll()

    override suspend fun readById(id: NanoId): Result<UserData?, Nothing?> = dataSource.readById(id)

    override suspend fun readByUsername(username: String): Result<UserData?, Nothing?> =
        dataSource.readByUsername(username)
}
