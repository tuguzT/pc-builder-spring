package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentEntity

/**
 * Basic interface for service of [ComponentEntity].
 */
interface ComponentService {
    /**
     * Inserts [entity] into the server repository.
     */
    suspend fun save(entity: ComponentEntity): ComponentEntity

    /**
     * Returns all data of type [ComponentEntity] from the server.
     */
    suspend fun getAll(): List<ComponentEntity>

    /**
     * Returns component found by [id], if any.
     */
    suspend fun findById(id: String): ComponentEntity?
}
