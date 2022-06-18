package io.github.tuguzt.pcbuilder.backend.plugins

import io.github.tuguzt.pcbuilder.backend.exceptions.BadCredentialsException
import io.github.tuguzt.pcbuilder.backend.exceptions.UserAlreadyExistsException
import io.github.tuguzt.pcbuilder.backend.exceptions.UserNotFoundException
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
        status(HttpStatusCode.BadRequest, HttpStatusCode.NotFound) { call, status ->
            logger.info { status }
            val error = DomainError(message = "$status", at = Clock.System.now())
            call.respond(status, error)
        }
        exception<Throwable> { call, cause ->
            logger.error(cause) { HttpStatusCode.InternalServerError }
            val error = DomainError(message = "${HttpStatusCode.InternalServerError}", at = Clock.System.now())
            call.respond(HttpStatusCode.InternalServerError, error)
        }
        exception<BadCredentialsException> { call, cause ->
            logger.error(cause) { "Bad credentials" }
            val error = DomainError(message = "Bad credentials", at = Clock.System.now())
            call.respond(HttpStatusCode.BadRequest, error)
        }
        exception<UserAlreadyExistsException> { call, cause ->
            logger.error(cause) { "User already exists" }
            val error = DomainError(message = "User already exists", at = Clock.System.now())
            call.respond(HttpStatusCode.BadRequest, error)
        }
        exception<UserNotFoundException> { call, cause ->
            logger.error(cause) { "User not found" }
            val error = DomainError(message = "User not found", at = Clock.System.now())
            call.respond(HttpStatusCode.NotFound, error)
        }
    }
}
