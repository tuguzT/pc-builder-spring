package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.security.JwtUtils
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
@Tag(name = "Пользователи", description = "Конечные сетевые точки обращения пользовательских данных")
class UserController(
    private val jwtUtils: JwtUtils,
    private val userService: UserService,
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

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
    ): ResponseEntity<UserData> {
        val accessToken = bearer.substringAfter("Bearer ")

        val username = jwtUtils.extractUsername(accessToken)
        return findById(username)
    }

    @GetMapping("id/{id}")
    @Operation(summary = "Поиск по ID", description = "Поиск пользователя в системе по его идентификатору")
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор пользователя")
        id: String,
    ): ResponseEntity<UserData> {
        logger.info { "Requested user with ID $id" }
        val user = userService.findById(id)
        if (user == null) {
            logger.info { "User with ID $id not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found user with ID $id" }
        return ResponseEntity.ok(user)
    }
}
