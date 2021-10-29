package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.ComponentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Implementation of the [ComponentService] interface.
 */
@Service
class ComponentServiceImpl(private val repository: ComponentRepository) : ComponentService {
    override suspend fun insert(item: ComponentEntity) = repository.save(item)

    override suspend fun getAll(): List<ComponentEntity> = repository.findAll()

    override suspend fun findById(id: String): ComponentEntity? = repository.findByIdOrNull(id)
}
