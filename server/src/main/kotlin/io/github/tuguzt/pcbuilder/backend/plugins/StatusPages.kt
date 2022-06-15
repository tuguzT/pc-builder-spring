package io.github.tuguzt.pcbuilder.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import mu.KLogger
import org.koin.ktor.ext.inject
import io.github.tuguzt.pcbuilder.domain.model.Error as DomainError

/**
 * Configures exception and status handling of the application.
 */
fun Application.configureStatusPages() {
    val logger: KLogger by inject()

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            logger.info { "404 Not Found" }
            val error = DomainError(message = "404 Not Found", at = Clock.System.now())
            call.respond(status, error)
        }
        exception<Throwable> { call, cause ->
            logger.error(cause) { "500 Internal Server Error" }
            val error = DomainError(message = "500 Internal Server Error", at = Clock.System.now())
            call.respond(HttpStatusCode.InternalServerError, error)
        }
        exception<UserAlreadyExistsException>() { call, cause ->
            logger.error(cause) { "User already exists" }
            val error = DomainError(message = "User already exists", at = Clock.System.now())
            call.respond(HttpStatusCode.BadRequest, error)
        }
        exception<UserNotFoundException>() { call, cause ->
            logger.error(cause) { "User not found" }
            val error = DomainError(message = "User not found", at = Clock.System.now())
            call.respond(HttpStatusCode.NotFound, error)
        }
        exception<BadCredentialsException>() { call, cause ->
            logger.error(cause) { "Bad credentials" }
            val error = DomainError(message = "Bad credentials", at = Clock.System.now())
            call.respond(HttpStatusCode.BadRequest, error)
        }
    }
}

class UserAlreadyExistsException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message)

class UserNotFoundException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message)

class BadCredentialsException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message)
