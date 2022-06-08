package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions.NotFoundException
import io.github.tuguzt.pcbuilder.backend.spring.security.JwtUtils
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ComponentService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.FavoriteComponentService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.UserService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
@Tag(name = "Пользователи", description = "Конечные сетевые точки обращения пользовательских данных")
class UserController(
    private val jwtUtils: JwtUtils,
    private val userService: UserService,
    private val componentService: ComponentService,
    private val favoriteComponentService: FavoriteComponentService,
) : KoinComponent {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val json: Json by inject()

    @GetMapping("all")
    @Operation(summary = "Все пользователи", description = "Получение данных обо всех пользователях системы")
    suspend fun allUsers(): List<UserData> {
        logger.info { "Requested all users" }
        return userService.getAll()
    }

    @GetMapping("current")
    @Operation(summary = "Текущий пользователь", description = "Получение данных текущего пользователя по токену")
    suspend fun current(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
    ): UserData {
        val token = bearer.substringAfter("Bearer ")

        val username = jwtUtils.extractUsername(token)
        return findByUsername(username)
    }

    @GetMapping("current/favorites/all")
    @Operation(
        summary = "Избранные компоненты",
        description = "Получение списка всех избранных компонентов текущего пользователя",
    )
    fun allFavoriteComponents(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
    ): String = runBlocking {
        val currentUser = current(bearer)
        val favoriteComponents = favoriteComponentService.getFavoriteComponents(currentUser)
        json.encodeToString(favoriteComponents)
    }

    @PostMapping("current/favorites/add/{componentId}")
    @Operation(
        summary = "Добавить в избранное",
        description = "Добавление нового компонента ПК в список изрбанных",
    )
    fun addToFavorites(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
        @PathVariable
        @Parameter(name = "Идентификатор компонента")
        componentId: NanoId,
    ): String = runBlocking {
        val currentUser = current(bearer)
        val component = componentService.findById(componentId, currentUser)
            ?: throw NotFoundException()
        val result = favoriteComponentService.addFavoriteComponent(currentUser, component)
        json.encodeToString(result)
    }

    @PostMapping("current/favorites/remove/{componentId}")
    @Operation(
        summary = "Удалить из избранных",
        description = "Удаление нового компонента ПК из списка изрбанных",
    )
    fun removeFromFavorites(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
        @PathVariable
        @Parameter(name = "Идентификатор компонента")
        componentId: NanoId,
    ): String = runBlocking {
        val currentUser = current(bearer)
        val component = componentService.findById(componentId, currentUser)
            ?: throw NotFoundException()
        val result = favoriteComponentService.removeFavoriteComponent(currentUser, component)
        json.encodeToString(result)
    }

    @GetMapping("id/{id}")
    @Operation(summary = "Поиск по ID", description = "Поиск пользователя в системе по его идентификатору")
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор пользователя")
        id: NanoId,
    ): UserData {
        logger.info { "Requested user with ID $id" }
        val user = userService.findById(id)
        if (user == null) {
            logger.info { "User with ID $id not found" }
            throw NotFoundException()
        }
        logger.info { "Found user with ID $id" }
        return user
    }

    @GetMapping("username/{username}")
    @Operation(
        summary = "Поиск по имени пользователя",
        description = "Поиск пользователя в системе по его имени пользователя",
    )
    suspend fun findByUsername(
        @PathVariable
        @Parameter(name = "Имя пользователя")
        username: String,
    ): UserData {
        logger.info { "Requested user with username $username" }
        val user = userService.findByUsername(username)
        if (user == null) {
            logger.info { "User with username $username not found" }
            throw NotFoundException()
        }
        logger.info { "Found user with username $username" }
        return user
    }
}
