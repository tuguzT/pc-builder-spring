package io.github.tuguzt.pcbuilder.backend.data.repository.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordUserDataSource
import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordUserRepository
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId

internal class PasswordUserRepositoryImpl(private val dataSource: PasswordUserDataSource) : PasswordUserRepository<Nothing?> {
    override suspend fun clear(): Result<Unit, Nothing?> = dataSource.clear()

    override suspend fun delete(item: PasswordUserData): Result<Unit, Nothing?> = dataSource.delete(item)

    override suspend fun readAll(): Result<List<PasswordUserData>, Nothing?> = dataSource.readAll()

    override suspend fun readById(id: NanoId): Result<PasswordUserData?, Nothing?> = dataSource.readById(id)

    override suspend fun save(item: PasswordUserData): Result<PasswordUserData, Nothing?> = dataSource.save(item)

    override suspend fun readByUsername(username: String): Result<PasswordUserData?, Nothing?> =
        dataSource.readByUsername(username)
}
