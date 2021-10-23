package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import io.github.tuguzt.pcbuilder.backend.spring.repository.schema.ComponentTable
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import kotlinx.coroutines.flow.asFlow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Implementation of the [ComponentRepository] interface.
 */
@Repository
class ComponentRepositoryImpl : ComponentRepository {
    /**
     * Creates the repository table if it is not exist.
     */
    @Bean
    @Transactional
    fun init() {
        SchemaUtils.create(ComponentTable)
    }

    override suspend fun insert(item: ComponentData) {
        newSuspendedTransaction { ComponentTable.insert(item.toRow()) }
    }

    override suspend fun getAll() =
        newSuspendedTransaction { ComponentTable.selectAll().map(ResultRow::fromRow) }.asFlow()

    override suspend fun update(item: ComponentData) {
        newSuspendedTransaction { ComponentTable.update(where = eqById(item.id), body = item.toRow()) }
    }

    override suspend fun delete(item: ComponentData) {
        newSuspendedTransaction { ComponentTable.deleteWhere(op = eqById(item.id)) }
    }

    override suspend fun findById(id: String) = newSuspendedTransaction {
        ComponentTable.select(eqById(id)).firstOrNull()?.fromRow()
    }
}

/**
 * An [operation][Op] which indicates if [id] is equal by [ComponentData.id] to some entity.
 */
private fun eqById(id: String): SqlExpressionBuilder.() -> Op<Boolean> = { ComponentTable.id eq id }

/**
 * Returns a builder of update operation for entity of type [ComponentData].
 */
private fun ComponentData.toRow(): ComponentTable.(UpdateBuilder<*>) -> Unit {
    val data = this
    return { updateBuilder ->
        updateBuilder[id] = data.id
        updateBuilder[name] = data.name
        updateBuilder[description] = data.description
        updateBuilder[weight] = data.weight `in` grams
        updateBuilder[length] = data.size.length `in` meters
        updateBuilder[width] = data.size.width `in` meters
        updateBuilder[height] = data.size.height `in` meters
    }
}

/**
 * Converts [ResultRow] into the entity of type [ComponentData].
 */
private fun ResultRow.fromRow() = ComponentData(
    id = this[ComponentTable.id],
    name = this[ComponentTable.name],
    description = this[ComponentTable.description],
    weightInGrams = this[ComponentTable.weight],
    lengthInMeters = this[ComponentTable.length],
    widthInMeters = this[ComponentTable.width],
    heightInMeters = this[ComponentTable.height],
)
