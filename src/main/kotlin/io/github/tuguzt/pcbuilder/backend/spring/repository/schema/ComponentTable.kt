package io.github.tuguzt.pcbuilder.backend.spring.repository.schema

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import org.jetbrains.exposed.sql.Table

/**
 * Table schema for [ComponentData].
 */
object ComponentTable : Table() {
    val id = varchar("id", 21)
    val name = varchar("name", 100).uniqueIndex()
    val description = text("description")
    val weight = double("weight")
    val length = double("length")
    val width = double("width")
    val height = double("height")

    override val primaryKey = PrimaryKey(id)
}
