package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import io.github.tuguzt.pcbuilder.backend.spring.repository.CRUDRepository

/**
 * [CRUDRepository] which contains data of type [ComponentData].
 */
interface ComponentRepository : CRUDRepository<ComponentData> {
    /**
     * Returns component found by [id], if any.
     */
    suspend fun findById(id: String): ComponentData?
}
