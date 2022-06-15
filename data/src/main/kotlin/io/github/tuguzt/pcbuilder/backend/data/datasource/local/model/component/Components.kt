package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents SQL table of [components][Component].
 */
internal object Components : IdTable<String>() {
    override val id = char(name = "id", length = 21).entityId()
    override val primaryKey = PrimaryKey(id)

    val name = varchar(name = "name", length = 256).uniqueIndex()
    val description = text(name = "description")
    val weightInGrams = double(name = "weight_in_g")
    val lengthInMillimeters = double(name = "length_in_mm")
    val widthInMillimeters = double(name = "width_in_mm")
    val heightInMillimeters = double(name = "height_in_mm")
    val manufacturer = reference(name = "manufacturer_id", Manufacturers, onDelete = ReferenceOption.CASCADE)
    val imageUri = text(name = "image_uri").nullable()
}
