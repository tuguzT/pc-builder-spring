package io.github.tuguzt.pcbuilder.backend.data.repository

import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.github.tuguzt.pcbuilder.domain.repository.util.crud.ClearRepository
import io.github.tuguzt.pcbuilder.domain.repository.util.crud.DeleteRepository
import io.github.tuguzt.pcbuilder.domain.repository.util.crud.ReadAllRepository
import io.github.tuguzt.pcbuilder.domain.repository.util.crud.ReadByIdRepository

interface UserRepository :
    ReadAllRepository<NanoId, UserData, Nothing?>,
    ReadByIdRepository<NanoId, UserData, Nothing?>,
    DeleteRepository<NanoId, UserData, Nothing?>,
    ClearRepository<NanoId, UserData, Nothing?> {

    suspend fun readByUsername(username: String): Result<UserData?, Nothing?>
}
