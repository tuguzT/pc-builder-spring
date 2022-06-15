package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component

import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Data Access Object of the [manufacturer][ManufacturerData]
 */
internal class Manufacturer(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Manufacturer>(Manufacturers)

    var name by Manufacturers.name
    var description by Manufacturers.description
}
