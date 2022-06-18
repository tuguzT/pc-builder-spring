package io.github.tuguzt.pcbuilder.backend.data.datasource.util.crud

import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

internal interface ReadAllDataSource<Id, T : Identifiable<Id>> {
    suspend fun readAll(): Result<List<T>, Nothing?>
}
