package io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.component

import io.github.tuguzt.pcbuilder.backend.data.datasource.local.model.nanoId
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Manufacturer
import org.jetbrains.exposed.dao.id.IdTable

/**
 * Represents SQL table of [manufacturers][Manufacturer].
 */
internal object Manufacturers : IdTable<NanoId>() {
    override val id = nanoId().entityId()
    override val primaryKey = PrimaryKey(id)

    val name = varchar(name = "name", length = 128).uniqueIndex()
    val description = text(name = "description")
}
