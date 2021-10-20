package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import io.github.tuguzt.pcbuilder.backend.spring.service.ComponentService
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import org.springframework.web.bind.annotation.*

/**
 * REST API controller of the server for [PC components][Component].
 */
@RestController
@RequestMapping("components")
class ComponentController(private val service: ComponentService) {
    /**
     * GET request which returns all PC components.
     */
    @GetMapping
    suspend fun index() = service.getAll()

    /**
     * POST request which inserts [component] into the server repository.
     */
    @PostMapping("insert")
    suspend fun insert(@RequestBody component: ComponentData) {
        service.insert(component)
    }
}
