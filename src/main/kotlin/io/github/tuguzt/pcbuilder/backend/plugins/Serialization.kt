package io.github.tuguzt.pcbuilder.backend.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Application.configureSerialization() {
    val json: Json by inject()

    install(ContentNegotiation) {
        json(json)
    }
}
