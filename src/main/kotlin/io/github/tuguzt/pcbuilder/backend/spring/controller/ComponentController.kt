package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions.NotFoundException
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ComponentService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

/**
 * REST API controller of the server for [PC components][Component].
 */
@RestController
@RequestMapping("components")
@Tag(name = "Компоненты ПК", description = "Конечные сетевые точки обращения данных компонентов ПК системы")
class ComponentController(
    private val userController: UserController,
    private val service: ComponentService,
) : KoinComponent {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val json: Json by inject()

    /**
     * GET request which returns all PC components.
     */
    @GetMapping("all")
    @Operation(summary = "Все компоненты", description = "Получение списка всех компонентов ПК в системе")
    suspend fun all(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
    ): String {
        val currentUser = userController.current(bearer)

        logger.info { "Requested all components" }
        val all = service.getAll(currentUser)
        return json.encodeToString(all)
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
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
        @PathVariable
        @Parameter(name = "Идентификатор компонента ПК")
        id: NanoId,
    ): PolymorphicComponent {
        val currentUser = userController.current(bearer)

        logger.info { "Requested component with ID $id" }
        val component = service.findById(id, currentUser)
        if (component == null) {
            logger.info { "Component with ID $id not found" }
            throw NotFoundException()
        }
        logger.info { "Found component with ID $id" }
        return component
    }

    /**
     * GET request which returns component found by [name], if any.
     */
    @GetMapping("name/{name}")
    @Operation(summary = "Поиск по названию", description = "Поиск компонента в системе по его названию")
    suspend fun findByName(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
        @PathVariable
        @Parameter(name = "Название компонента ПК")
        name: String,
    ): PolymorphicComponent {
        val currentUser = userController.current(bearer)

        logger.info { "Requested component with name $name" }
        val component = service.findByName(name, currentUser)
        if (component == null) {
            logger.info { "Component with name $name not found" }
            throw NotFoundException()
        }
        logger.info { "Found component with name $name" }
        return component
    }
}
