package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.ComponentRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.ComponentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Implementation of the [ComponentService] interface.
 */
@Service
class ComponentServiceImpl(private val repository: ComponentRepository) : ComponentService {
    override suspend fun save(item: ComponentEntity) = withContext(Dispatchers.IO) { repository.save(item) }

    override suspend fun delete(item: ComponentEntity) = withContext(Dispatchers.IO) { repository.delete(item) }

    override suspend fun getAll(): List<ComponentEntity> = withContext(Dispatchers.IO) { repository.findAll() }

    override suspend fun findById(id: String) = withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }

    override suspend fun deleteById(id: String) = withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: ComponentEntity) =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }
}
