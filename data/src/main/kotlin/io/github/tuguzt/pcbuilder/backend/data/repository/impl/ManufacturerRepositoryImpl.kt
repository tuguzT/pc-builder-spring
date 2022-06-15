package io.github.tuguzt.pcbuilder.backend.data.repository.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.repository.component.ManufacturerRepository

internal class ManufacturerRepositoryImpl(
    private val dataSource: ManufacturerDataSource,
) : ManufacturerRepository<Nothing?> {

    override suspend fun readByName(name: String): Result<ManufacturerData?, Nothing?> = dataSource.readByName(name)

    override suspend fun clear(): Result<Unit, Nothing?> = dataSource.clear()

    override suspend fun delete(item: ManufacturerData): Result<Unit, Nothing?> = dataSource.delete(item)

    override suspend fun readAll(): Result<List<ManufacturerData>, Nothing?> = dataSource.readAll()

    override suspend fun readById(id: NanoId): Result<ManufacturerData?, Nothing?> = dataSource.readById(id)

    override suspend fun save(item: ManufacturerData): Result<ManufacturerData, Nothing?> = dataSource.save(item)
}
