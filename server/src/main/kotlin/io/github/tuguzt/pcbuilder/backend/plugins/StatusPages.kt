package io.github.tuguzt.pcbuilder.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

/**
 * Configures exception and status handling of the application.
 */
fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500 Internal Server Error: ${cause.message}",
                status = HttpStatusCode.InternalServerError,
            )
        }
    }
}
