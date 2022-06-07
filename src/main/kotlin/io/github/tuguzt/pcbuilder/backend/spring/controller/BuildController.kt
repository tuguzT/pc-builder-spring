package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions.NotFoundException
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.BuildService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("builds")
@Tag(name = "Сборки ПК", description = "Конечные сетевые точки обращения для сборок ПК")
class BuildController(
    private val userController: UserController,
    private val service: BuildService,
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    /**
     * GET request which returns all PC builds.
     */
    @GetMapping("all")
    @Operation(summary = "Все сборки ПК", description = "Получение списка всех сборок ПК в системе")
    suspend fun all(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
    ): List<BuildData> {
        val currentUser = userController.current(bearer)

        logger.info { "Requested all builds" }
        return service.getAll(currentUser)
    }

    /**
     * GET request which returns PC build found by [id], if any.
     */
    @GetMapping("id/{id}")
    @Operation(summary = "Поиск по идентификатору", description = "Поиск сборки ПК в системе по ее идентификатору")
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор компонента ПК")
        id: NanoId,
    ): BuildData {
        logger.info { "Requested build with ID $id" }
        val build = service.findById(id)
        if (build == null) {
            logger.info { "Build with ID $id not found" }
            throw NotFoundException()
        }
        logger.info { "Found build with ID $id" }
        return build
    }

    /**
     * GET request which returns case found by [name], if any.
     */
    @GetMapping("name/{name}")
    @Operation(summary = "Поиск по названию", description = "Поиск сборки ПК в системе по его названию")
    suspend fun findByName(
        @PathVariable
        @Parameter(name = "Название сборки ПК")
        name: String,
    ): BuildData {
        logger.info { "Requested build with name $name" }
        val build = service.findByName(name)
        if (build == null) {
            logger.info { "Build with name $name not found" }
            throw NotFoundException()
        }
        logger.info { "Found build with name $name" }
        return build
    }

    @PostMapping("save")
    @Operation(summary = "Сохранить сборку ПК", description = "Сохранение сборки ПК в системе текущего пользователя")
    suspend fun save(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
        @RequestBody
        @Parameter(name = "Данные сборки ПК для сохранения в системе")
        build: BuildData,
    ): BuildData {
        val currentUser = userController.current(bearer)

        logger.info { "Attempting to save PC build $build" }
        val buildData = service.save(build, currentUser)
        logger.info { "PC build $build was saved" }
        return buildData
    }

    @PostMapping("delete")
    @Operation(summary = "Удалить сборку ПК", description = "Удаление сборки ПК из системы текущего пользователя")
    suspend fun delete(
        @RequestBody
        @Parameter(name = "Данные сборки ПК для удаления из системы")
        build: BuildData,
    ) {
        logger.info { "Attempting to delete PC build $build" }
        service.delete(build)
        logger.info { "PC build $build was deleted" }
    }
}
