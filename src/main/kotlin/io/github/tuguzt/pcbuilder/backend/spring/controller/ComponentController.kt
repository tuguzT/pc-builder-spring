package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ComponentService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    suspend fun all(): List<ComponentData> {
        logger.info { "Requested all components" }
        return service.getAll()
    }

    /**
     * GET request which returns component found by [id], if any.
     */
    @GetMapping("id/{id}")
    @Operation(
        summary = "Поиск по идентификатору",
        description = "Поиск компонента в системе по его идентификатору",
    )
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор компонента ПК")
        id: NanoId,
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
     * GET request which returns component found by [name], if any.
     */
    @GetMapping("name/{name}")
    @Operation(summary = "Поиск по названию", description = "Поиск компонента в системе по его названию")
    suspend fun findByName(
        @PathVariable
        @Parameter(name = "Название компонента ПК")
        name: String,
    ): ResponseEntity<ComponentData> {
        logger.info { "Requested component with name $name" }
        val component = service.findByName(name)
        if (component == null) {
            logger.info { "Component with name $name not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found component with name $name" }
        return ResponseEntity.ok(component)
    }
}
