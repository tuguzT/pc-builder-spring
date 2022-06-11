package io.github.tuguzt.pcbuilder.backend.plugins

import io.github.tuguzt.pcbuilder.backend.routes.userRoutes
import io.ktor.server.application.*

/**
 * Configures routing of the application.
 */
fun Application.configureRouting() {
    userRoutes()
}
