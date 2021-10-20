package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData
import io.github.tuguzt.pcbuilder.backend.spring.service.ComponentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("components")
class ComponentController(private val service: ComponentService) {
    @GetMapping
    fun index() = service.getAll()

    @PostMapping("insert")
    fun insert(@RequestBody component: ComponentData) {
        service.insert(component)
    }
}
