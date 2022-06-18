package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.backend.data.datasource.util.crud.ClearDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.util.crud.DeleteDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.util.crud.ReadAllDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.util.crud.ReadByIdDataSource
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

internal interface UserDataSource :
    ClearDataSource<NanoId, UserData>,
    DeleteDataSource<NanoId, UserData>,
    ReadAllDataSource<NanoId, UserData>,
    ReadByIdDataSource<NanoId, UserData> {

    suspend fun readByUsername(username: String): Result<UserData?, Nothing?>
}
