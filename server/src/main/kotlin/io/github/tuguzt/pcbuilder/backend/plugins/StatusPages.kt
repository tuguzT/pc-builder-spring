package io.github.tuguzt.pcbuilder.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Configures exception and status handling of the application.
 */
fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            logger.info { "404: Resource Not Found" }
            call.respondText(text = "404: Resource Not Found", status = status)
        }
        exception<Throwable> { call, cause ->
            logger.info(cause) { "500: Internal Server Error" }
            call.respondText(
                text = "500: Internal Server Error: ${cause.message}",
                status = HttpStatusCode.InternalServerError,
            )
        }
    }
}

class UserAlreadyExistsException(override val message: String) : Exception(message)

class UserNotFoundException(override val message: String) : Exception(message)

class BadCredentialsException(override val message: String) : Exception(message)
