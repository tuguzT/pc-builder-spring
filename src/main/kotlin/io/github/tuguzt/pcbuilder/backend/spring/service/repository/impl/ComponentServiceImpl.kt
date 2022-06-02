package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.ComponentRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ComponentService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Implementation of the [ComponentService] interface.
 */
@Service
class ComponentServiceImpl(private val repository: ComponentRepository) : ComponentService {
    override suspend fun save(item: ComponentData): ComponentData =
        withContext(Dispatchers.IO) { repository.save(item.toEntity()) }.toData()

    override suspend fun delete(item: ComponentData) =
        withContext(Dispatchers.IO) { repository.delete(item.toEntity()) }

    override suspend fun getAll(): List<ComponentData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(ComponentEntity::toData)

    override suspend fun findById(id: NanoId): ComponentData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }?.toData()

    override suspend fun deleteById(id: NanoId) = withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: ComponentData) =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }
}
