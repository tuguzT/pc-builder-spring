package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.ComponentDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.Components
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * [Component data source][ComponentDataSource] implementation which uses local database.
 */
class LocalComponentDataSource(
    private val database: Database,
    private val context: CoroutineDispatcher = Dispatchers.IO,
) : ComponentDataSource {

    override suspend fun readByName(name: String): Result<PolymorphicComponent?, Nothing?> = runCatching {
        val component = newSuspendedTransaction(context, database) {
            ComponentEntity.find { Components.name eq name }.limit(1).firstOrNull()
        }
        component?.toData()
    }.toResult()

    override suspend fun readAll(): Result<List<PolymorphicComponent>, Nothing?> = runCatching {
        val components = newSuspendedTransaction(context, database) {
            ComponentEntity.all().toList()
        }
        components.map { it.toData() }
    }.toResult()

    override suspend fun readById(id: NanoId): Result<PolymorphicComponent?, Nothing?> = runCatching {
        val component = newSuspendedTransaction(context, database) {
            ComponentEntity.findById(id)
        }
        component?.toData()
    }.toResult()

    override suspend fun save(item: PolymorphicComponent): Result<PolymorphicComponent, Nothing?> = runCatching {
        val component = newSuspendedTransaction(context, database) {
            when (val manufacturer = ComponentEntity.findById(item.id)) {
                null -> item.newEntity()
                else -> item.saveIntoEntity(manufacturer)
            }
        }
        component.toData()
    }.toResult()

    override suspend fun delete(item: PolymorphicComponent): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            ComponentEntity.findById(item.id)?.delete()
        }
        Unit
    }.toResult()

    override suspend fun clear(): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            Components.deleteAll()
        }
        Unit
    }.toResult()

    private fun ComponentEntity.toData(): PolymorphicComponent = TODO()
}
