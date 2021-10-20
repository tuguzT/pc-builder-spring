package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.ComponentRepository
import org.springframework.stereotype.Service

@Service
class ComponentServiceImpl(private val repository: ComponentRepository) : ComponentService {
    override fun insert(item: ComponentData) = repository.insert(item)

    override fun getAll() = repository.getAll()
}
