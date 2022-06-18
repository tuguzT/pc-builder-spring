package io.github.tuguzt.pcbuilder.backend.data.repository.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.repository.component.ComponentRepository

internal class ComponentRepositoryImpl(private val dataSource: ComponentDataSource) : ComponentRepository<Nothing?> {
    override suspend fun clear(): Result<Unit, Nothing?> = dataSource.clear()

    override suspend fun delete(item: PolymorphicComponent): Result<Unit, Nothing?> = dataSource.delete(item)

    override suspend fun readAll(): Result<List<PolymorphicComponent>, Nothing?> = dataSource.readAll()

    override suspend fun readById(id: NanoId): Result<PolymorphicComponent?, Nothing?> = dataSource.readById(id)

    override suspend fun readByName(name: String): Result<PolymorphicComponent?, Nothing?> = dataSource.readByName(name)

    override suspend fun save(item: PolymorphicComponent): Result<PolymorphicComponent, Nothing?> =
        dataSource.save(item)
}
