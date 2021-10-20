package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import kotlinx.coroutines.flow.Flow

/**
 * Basic interface for service of [ComponentData].
 */
interface ComponentService {
    /**
     * Inserts [item] into the server repository.
     */
    suspend fun insert(item: ComponentData)

    /**
     * Returns all data of type [ComponentData] from the server.
     */
    suspend fun getAll(): Flow<ComponentData>
}
