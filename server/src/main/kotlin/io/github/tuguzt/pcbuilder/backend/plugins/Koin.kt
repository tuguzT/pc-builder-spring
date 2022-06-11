package io.github.tuguzt.pcbuilder.backend.plugins

import io.github.tuguzt.pcbuilder.backend.di.appModule
import io.github.tuguzt.pcbuilder.backend.di.dataModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

/**
 * Configures Koin dependency injection of the application.
 */
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule, dataModule)
    }
}
