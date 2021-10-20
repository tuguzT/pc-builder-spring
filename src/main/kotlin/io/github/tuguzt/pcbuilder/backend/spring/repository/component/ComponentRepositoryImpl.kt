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

@Repository
class ComponentRepositoryImpl : ComponentRepository {
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
        newSuspendedTransaction { ComponentTable.update(where = eqById(item), body = item.toRow()) }
    }

    override suspend fun delete(item: ComponentData) {
        newSuspendedTransaction { ComponentTable.deleteWhere(op = eqById(item)) }
    }
}

private fun eqById(item: ComponentData): SqlExpressionBuilder.() -> Op<Boolean> = {
    ComponentTable.id eq item.id
}

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

private fun ResultRow.fromRow() = ComponentData(
    id = this[ComponentTable.id],
    name = this[ComponentTable.name],
    description = this[ComponentTable.description],
    weightInGrams = this[ComponentTable.weight],
    lengthInMeters = this[ComponentTable.length],
    widthInMeters = this[ComponentTable.width],
    heightInMeters = this[ComponentTable.height],
)
