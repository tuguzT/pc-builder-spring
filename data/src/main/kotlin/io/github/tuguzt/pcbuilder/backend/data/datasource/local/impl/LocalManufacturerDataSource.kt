package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import io.github.tuguzt.pcbuilder.backend.data.datasource.ManufacturerDataSource
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.Manufacturer
import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component.Manufacturers
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import io.github.tuguzt.pcbuilder.domain.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * [Manufacturer data source][ManufacturerDataSource] implementation which uses local database.
 */
internal class LocalManufacturerDataSource(
    private val database: Database,
    private val context: CoroutineDispatcher = Dispatchers.IO,
) : ManufacturerDataSource {

    override suspend fun readAll(): Result<List<ManufacturerData>, Nothing?> = runCatching {
        val manufacturers = newSuspendedTransaction(context, database) {
            Manufacturer.all().toList()
        }
        manufacturers.map { it.toData() }
    }.toResult()

    override suspend fun readById(id: NanoId): Result<ManufacturerData?, Nothing?> = runCatching {
        val manufacturer = newSuspendedTransaction(context, database) {
            Manufacturer.findById(id = "$id")
        }
        manufacturer?.toData()
    }.toResult()

    override suspend fun save(item: ManufacturerData): Result<ManufacturerData, Nothing?> = runCatching {
        val manufacturer = newSuspendedTransaction(context, database) {
            when (val manufacturer = Manufacturer.findById(id = "${item.id}")) {
                null -> Manufacturer.new(id = "${item.id}") {
                    name = item.name
                    description = item.description
                }
                else -> {
                    manufacturer.name = item.name
                    manufacturer.description = item.description
                    manufacturer
                }
            }
        }
        manufacturer.toData()
    }.toResult()

    override suspend fun delete(item: ManufacturerData): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            Manufacturer.findById(id = "${item.id}")?.delete()
        }
        Unit
    }.toResult()

    override suspend fun clear(): Result<Unit, Nothing?> = runCatching {
        newSuspendedTransaction(context, database) {
            Manufacturers.deleteAll()
        }
        Unit
    }.toResult()

    override suspend fun readByName(name: String): Result<ManufacturerData?, Nothing?> = runCatching {
        val manufacturer = newSuspendedTransaction(context, database) {
            Manufacturer.find { Manufacturers.name eq name }.limit(1).firstOrNull()
        }
        manufacturer?.toData()
    }.toResult()

    private fun Manufacturer.toData(): ManufacturerData = ManufacturerData(id.value.let(::NanoId), name, description)
}