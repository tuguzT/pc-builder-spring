package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import kotlinx.coroutines.flow.Flow

interface ComponentService {
    suspend fun insert(item: ComponentData)

    suspend fun getAll(): Flow<ComponentData>
}
