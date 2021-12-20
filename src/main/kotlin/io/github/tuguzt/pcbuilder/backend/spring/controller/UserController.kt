package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.security.JwtUtils
import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import io.github.tuguzt.pcbuilder.backend.spring.service.UserOAuth2Service
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("users")
class UserController(
    private val jwtUtils: JwtUtils,
    private val userService: UserService,
    private val userNamePasswordService: UserNamePasswordService,
    private val userOAuth2Service: UserOAuth2Service,
) {
    @GetMapping("all")
    suspend fun allUsers() = userService.getAll()

    @GetMapping("current")
    suspend fun current(@RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<UserEntity> {
        val token = bearer.substringAfter("Bearer ")
        val username = jwtUtils.extractUsername(token)
        return findByUsername(username)
    }

    @GetMapping("id/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<UserEntity> {
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
    suspend fun findByUsername(@PathVariable username: String): ResponseEntity<UserEntity> {
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
