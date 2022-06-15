package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Database entity of the [component][PolymorphicComponent]
 */
internal class ComponentEntity(id: EntityID<NanoId>) : Entity<NanoId>(id) {
    companion object : EntityClass<NanoId, ComponentEntity>(Components)

    var name by Components.name
    var description by Components.description
    var weightInGrams by Components.weightInGrams
    var lengthInMillimeters by Components.lengthInMillimeters
    var widthInMillimeters by Components.widthInMillimeters
    var heightInMillimeters by Components.heightInMillimeters
    var manufacturer by ManufacturerEntity referencedOn Components.manufacturer
    var imageUri by Components.imageUri
}
