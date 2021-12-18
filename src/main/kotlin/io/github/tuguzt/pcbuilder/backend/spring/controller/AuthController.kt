package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions.UserAlreadyExistsException
import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.security.JwtUtils
import io.github.tuguzt.pcbuilder.backend.spring.security.UserDetailsService
import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import io.github.tuguzt.pcbuilder.backend.spring.service.UserOAuth2Service
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val userDetailsService: UserDetailsService,
    private val userNamePasswordService: UserNamePasswordService,
    private val userOAuth2Service: UserOAuth2Service,
) {
    @PostMapping("auth")
    suspend fun auth(@RequestBody user: UserNamePasswordEntity): ResponseEntity<String> {
        val authentication = UsernamePasswordAuthenticationToken(user.username, user.password)
        authenticationManager.authenticate(authentication)

        val userDetails = userDetailsService.loadUserByUsername(user.username)
        val token = jwtUtils.generateToken(userDetails)
        logger.info { "User with username ${user.username} successfully authenticated" }
        return ResponseEntity.ok(token)
    }

    protected suspend fun checkUserNotExists(user: UserNamePasswordEntity) {
        if (userNamePasswordService.findByUsername(user.username) == null)
            throw UserAlreadyExistsException("User with username ${user.username} already exists")
    }

    @PostMapping("register")
    suspend fun register(@RequestBody user: UserNamePasswordEntity): ResponseEntity<String> {
        checkUserNotExists(user)

        @Suppress("NAME_SHADOWING")
        val user = UserNamePasswordEntity(
            username = user.username,
            password = passwordEncoder.encode(user.password),
            role = UserRole.User,
            email = null,
            imageUri = null,
        )
        userNamePasswordService.save(user)

        val response = auth(user)
        logger.info { "User with username ${user.username} successfully registered" }
        return response
    }

    @PostMapping("oauth2")
    suspend fun oauth2() {
        TODO()
    }
}
