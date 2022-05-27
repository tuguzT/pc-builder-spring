package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.service.ComponentService
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST API controller of the server for [PC components][Component].
 */
@RestController
@RequestMapping("components")
@Tag(name = "Компоненты ПК", description = "Конечные сетевые точки обращения данных компонентов ПК системы")
class ComponentController(private val service: ComponentService) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    /**
     * GET request which returns all PC components.
     */
    @GetMapping("all")
    @Operation(summary = "Все компоненты", description = "Получение списка всех компонентов ПК в системе")
    suspend fun allComponents(): List<ComponentData> = service.getAll()

    /**
     * GET request which returns component found by [id], if any.
     */
    @GetMapping("id/{id}")
    @Operation(summary = "Поиск по ID", description = "Поиск компонента в системе по его идентификатору")
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор компонента ПК")
        id: String,
    ): ResponseEntity<ComponentData> {
        logger.info { "Requested component with ID $id" }
        val component = service.findById(id)
        if (component == null) {
            logger.info { "Component with ID $id not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found component with ID $id" }
        return ResponseEntity.ok(component)
    }

    /**
     * POST request which inserts [component] into the server repository.
     */
    @PostMapping("save")
    @Operation(summary = "Сохранение компонента", description = "Сохранить данные компонента в системе")
    suspend fun save(
        @RequestBody
        @Parameter(name = "Данные компонента для сохранения")
        component: ComponentData,
    ): ComponentData {
        val data = service.save(component)
        logger.info { "Inserted component with ID ${data.id}" }
        return data
    }
}
