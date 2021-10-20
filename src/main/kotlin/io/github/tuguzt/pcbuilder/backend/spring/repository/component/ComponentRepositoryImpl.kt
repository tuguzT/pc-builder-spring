package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import io.github.tuguzt.pcbuilder.backend.spring.repository.schema.ComponentTable
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.grams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ComponentRepositoryImpl : ComponentRepository {
    override fun insert(item: ComponentData) {
        ComponentTable.insert(item.toRow())
    }

    override fun getAll(): List<ComponentData> = ComponentTable.selectAll().map(ResultRow::fromRow)

    override fun update(item: ComponentData) {
        ComponentTable.update(where = eqById(item), body = item.toRow())
    }

    override fun delete(item: ComponentData) {
        ComponentTable.deleteWhere(op = eqById(item))
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
