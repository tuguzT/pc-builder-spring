package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.security.JwtUtils
import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import io.github.tuguzt.pcbuilder.backend.spring.service.UserOAuth2Service
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
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
    private val userNamePasswordService: UserNamePasswordService,
    private val userOAuth2Service: UserOAuth2Service,
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @GetMapping("all")
    @Operation(summary = "Все пользователи", description = "Получение данных обо всех пользователях системы")
    suspend fun allUsers(): List<UserEntity> {
        logger.info { "Requested all users" }
        return userService.getAll()
    }

    @GetMapping("current")
    @Operation(summary = "Текущий пользователь", description = "Получение данных текущего пользователя по токену")
    suspend fun current(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        @Parameter(name = "Токен пользователя с префиксом 'Bearer '")
        bearer: String,
    ): ResponseEntity<UserEntity> {
        val accessToken = bearer.substringAfter("Bearer ")

        val oauth2User = userOAuth2Service.findByAccessToken(accessToken)
        if (oauth2User != null) {
            logger.info { "OAuth 2.0 user was found" }
            return ResponseEntity.ok(oauth2User.user)
        }

        val username = jwtUtils.extractUsername(accessToken)
        return findByUsername(username)
    }

    @GetMapping("id/{id}")
    @Operation(summary = "Поиск по ID", description = "Поиск пользователя в системе по его идентификатору")
    suspend fun findById(
        @PathVariable
        @Parameter(name = "Идентификатор пользователя")
        id: String,
    ): ResponseEntity<UserEntity> {
        logger.info { "Requested user with ID $id" }
        val namePasswordUser = userNamePasswordService.findById(id)
        if (namePasswordUser == null) {
            logger.info { "User with ID $id not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found user with ID $id" }
        return ResponseEntity.ok(namePasswordUser as UserEntity)
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
    ): ResponseEntity<UserEntity> {
        logger.info { "Requested user with username $username" }
        val user = userNamePasswordService.findByUsername(username)
        if (user == null) {
            logger.info { "User with username $username not found" }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        logger.info { "Found user with username $username" }
        return ResponseEntity.ok(user as UserEntity)
    }
}
