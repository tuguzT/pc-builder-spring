package io.github.tuguzt.pcbuilder.backend.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.tuguzt.pcbuilder.backend.data.model.PasswordUserData
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordHashRepository
import io.github.tuguzt.pcbuilder.backend.data.repository.PasswordUserRepository
import io.github.tuguzt.pcbuilder.backend.exceptions.BadCredentialsException
import io.github.tuguzt.pcbuilder.backend.exceptions.UserAlreadyExistsException
import io.github.tuguzt.pcbuilder.backend.exceptions.UserNotFoundException
import io.github.tuguzt.pcbuilder.backend.plugins.koin.JwtConfig
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserCredentialsData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserTokenData
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import io.github.tuguzt.pcbuilder.domain.usecase.CheckPasswordUseCase
import io.github.tuguzt.pcbuilder.domain.usecase.CheckUsernameUseCase
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject
import java.util.*
import kotlin.time.Duration.Companion.days

/**
 * All authentication routes of the application.
 */
fun Application.authRoutes() {
    routing {
        loginRoute()
        registerRoute()
    }
}

fun Route.loginRoute() {
    val userRepository: PasswordUserRepository<Nothing?> by inject()
    val passwordHasher: PasswordHashRepository by inject()
    val jwtConfig: JwtConfig by inject()

    post("/login") {
        val credentials = checkedCredentials()

        val byUsername = when (val result = userRepository.readByUsername(credentials.username)) {
            is Result.Error -> throw checkNotNull(result.cause)
            is Result.Success -> result.data
        }
        byUsername ?: throw UserNotFoundException("No user with username ${credentials.username}")

        val passwordMatches = when (val result = passwordHasher.verify(byUsername.password, credentials.password)) {
            is Result.Error -> throw checkNotNull(result.cause)
            is Result.Success -> result.data
        }
        if (!passwordMatches)
            throw BadCredentialsException("Wrong password for the user with username ${credentials.username}")

        val token = createToken(jwtConfig, byUsername)
        call.respond(message = UserTokenData(token))
    }
}

fun Route.registerRoute() {
    val userRepository: PasswordUserRepository<Nothing?> by inject()
    val passwordHasher: PasswordHashRepository by inject()
    val jwtConfig: JwtConfig by inject()

    post("/register") {
        val credentials = checkedCredentials()

        val byUsername = when (val result = userRepository.readByUsername(credentials.username)) {
            is Result.Error -> throw checkNotNull(result.cause)
            is Result.Success -> result.data
        }
        if (byUsername != null)
            throw UserAlreadyExistsException("User with username ${credentials.username} already exists")

        val newUser = kotlin.run {
            val data = PasswordUserData(
                id = randomNanoId(),
                role = UserRole.User,
                username = credentials.username,
                email = null,
                imageUri = null,
                password = when (val result = passwordHasher.hash(credentials.password)) {
                    is Result.Error -> throw checkNotNull(result.cause)
                    is Result.Success -> result.data
                },
            )
            when (val result = userRepository.save(data)) {
                is Result.Error -> throw checkNotNull(result.cause)
                is Result.Success -> result.data
            }
        }

        val token = createToken(jwtConfig, newUser)
        call.respond(message = UserTokenData(token))
    }
}

/**
 * Receive [credentials data][UserCredentialsData] from the context and check it.
 */
private suspend fun PipelineContext<*, ApplicationCall>.checkedCredentials(): UserCredentialsData {
    val credentials = call.receive<UserCredentialsData>()
    return credentials.also {
        val checkUsername: CheckUsernameUseCase by application.inject()
        val checkPassword: CheckPasswordUseCase by application.inject()
        if (!checkUsername(it.username)) throw BadCredentialsException("Username is not valid")
        if (!checkPassword(it.password)) throw BadCredentialsException("Password is not valid")
    }
}

/**
 * Creates JWT token from provided [user].
 */
private fun createToken(jwtConfig: JwtConfig, user: User): String =
    JWT.create()
        .withIssuer(jwtConfig.issuer)
        .withSubject("${user.id}")
        .withExpiresAt(Date(System.currentTimeMillis() + 1.days.inWholeMilliseconds))
        .sign(Algorithm.HMAC256(jwtConfig.secret))
