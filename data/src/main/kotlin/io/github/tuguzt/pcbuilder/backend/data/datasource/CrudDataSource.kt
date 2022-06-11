package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

interface CrudDataSource<Id, T : Identifiable<Id>> {
    suspend fun readAll(): Result<List<T>, Nothing?>

    suspend fun readById(id: Id): Result<T?, Nothing?>

    suspend fun save(item: T): Result<T, Nothing?>

    suspend fun delete(item: T): Result<Unit, Nothing?>

    suspend fun clear(): Result<Unit, Nothing?>
}
