package io.github.tuguzt.pcbuilder.backend

import io.github.tuguzt.pcbuilder.backend.plugins.configureKoin
import io.github.tuguzt.pcbuilder.backend.plugins.configureRouting
import io.github.tuguzt.pcbuilder.backend.plugins.configureSerialization
import io.github.tuguzt.pcbuilder.backend.plugins.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

/**
 * Main module of the application.
 */
fun Application.module() {
    configureKoin()

    configureSerialization()
    configureRouting()
    configureStatusPages()
}

/**
 * Application entry point.
 */
fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}
