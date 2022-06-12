package io.github.tuguzt.pcbuilder.backend.plugins

import io.github.tuguzt.pcbuilder.backend.data.di.dataModule
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import org.h2.Driver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import io.github.tuguzt.pcbuilder.domain.model.serialization.json as domainJson

/**
 * Configures Koin dependency injection of the application.
 */
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule, dataModule)
    }
}

val Application.appModule
    get() = module {
        singleOf(::json)
        singleOf(::jwtConfig)
    }

val Application.dataModule
    get() = dataModule(
        url = environment.config.property(path = "db.url").getString(),
        driver = requireNotNull(Driver::class.qualifiedName),
    )

private fun json(): Json = Json(domainJson) {}
