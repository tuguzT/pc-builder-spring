package io.github.tuguzt.pcbuilder.backend.data.repository

import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.repository.util.CrudRepository

interface PasswordUserRepository<Error> : CrudRepository<NanoId, PasswordUserData, Error> {
    suspend fun readByUsername(username: String): Result<PasswordUserData?, Error>
}
