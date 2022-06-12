package io.github.tuguzt.pcbuilder.backend

import io.github.tuguzt.pcbuilder.backend.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

/**
 * Main module of the application.
 */
fun Application.module() {
    configureKoin()

    configureSerialization()
    configureSecurity()
    configureRouting()
    configureStatusPages()
}

/**
 * Application entry point.
 */
fun main(args: Array<String>) = EngineMain.main(args)
