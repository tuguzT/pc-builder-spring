package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData

interface ComponentService {
    fun insert(item: ComponentData)

    fun getAll(): List<ComponentData>
}
