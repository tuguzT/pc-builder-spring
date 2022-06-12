package io.github.tuguzt.pcbuilder.backend.data.repository.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordHashDataSource
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordHashRepository
import io.github.tuguzt.pcbuilder.domain.Result

internal class PasswordHashRepositoryImpl(private val dataSource: PasswordHashDataSource) : PasswordHashRepository {
    override fun hash(password: String): Result<String, Nothing?> = dataSource.hash(password)

    override fun verify(hash: String, password: String): Result<Boolean, Nothing?> = dataSource.verify(hash, password)
}
