package io.github.tuguzt.pcbuilder.backend.spring.controller

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserNamePasswordRepository
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserOAuth2Repository
import io.github.tuguzt.pcbuilder.backend.spring.security.JwtUtils
import io.github.tuguzt.pcbuilder.backend.spring.security.UserDetailsService
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService,
    private val jwtUtils: JwtUtils,
    private val userNamePasswordRepository: UserNamePasswordRepository,
    private val userOAuth2Repository: UserOAuth2Repository,
) {
    @PostMapping("auth")
    suspend fun auth(@RequestBody user: UserNamePasswordEntity) = try {
        val authentication = UsernamePasswordAuthenticationToken(user.username, user.password)
        authenticationManager.authenticate(authentication)

        val userDetails = userDetailsService.loadUserByUsername(user.username)
        val token = jwtUtils.generateToken(userDetails)
        ResponseEntity.ok(token)
    } catch (e: BadCredentialsException) {
        logger.warn(e) { "Incorrect username or password" }
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    } catch (e: UsernameNotFoundException) {
        logger.warn(e) { "Username with username ${user.username} not found" }
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    } catch (e: Exception) {
        logger.error(e) { "Authentication internal error" }
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }

    @PostMapping("register")
    suspend fun register(@RequestBody user: UserNamePasswordEntity): ResponseEntity<String> = try {
        withContext(Dispatchers.IO) {
            require(userNamePasswordRepository.findByUsername(user.username) == null) {
                "User with username ${user.username} already exists"
            }
            @Suppress("NAME_SHADOWING")
            val user = UserNamePasswordEntity(
                username = user.username,
                password = passwordEncoder.encode(user.password),
                role = UserRole.User,
                email = null,
                imageUri = null,
            )
            userNamePasswordRepository.save(user)
        }

        auth(user)
    } catch (e: IllegalArgumentException) {
        logger.warn(e) { e.message }
        ResponseEntity.badRequest().build()
    } catch (e: Exception) {
        logger.error(e) { "Registration internal error" }
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }

    @PostMapping("oauth2")
    suspend fun oauth2() {
        TODO()
    }
}
