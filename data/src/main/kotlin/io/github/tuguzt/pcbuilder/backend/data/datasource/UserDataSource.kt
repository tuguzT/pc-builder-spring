package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

interface UserDataSource : CrudDataSource<NanoId, UserData> {
    suspend fun readByUsername(username: String): Result<UserData?, Nothing?>
}
