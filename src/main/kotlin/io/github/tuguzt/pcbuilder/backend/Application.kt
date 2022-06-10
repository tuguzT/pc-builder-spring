package io.github.tuguzt.pcbuilder.backend

import io.github.tuguzt.pcbuilder.backend.plugins.configureRouting
import io.github.tuguzt.pcbuilder.backend.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
