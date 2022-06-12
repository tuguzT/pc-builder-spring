package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId

interface PasswordUserDataSource : CrudDataSource<NanoId, PasswordUserData> {
    suspend fun readByUsername(username: String): Result<PasswordUserData?, Nothing?>
}
