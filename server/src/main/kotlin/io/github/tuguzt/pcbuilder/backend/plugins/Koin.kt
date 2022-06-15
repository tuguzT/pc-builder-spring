package io.github.tuguzt.pcbuilder.backend.plugins

import io.github.tuguzt.pcbuilder.backend.data.di.dataModule
import io.github.tuguzt.pcbuilder.domain.usecase.CheckPasswordUseCase
import io.github.tuguzt.pcbuilder.domain.usecase.CheckUsernameUseCase
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import mu.KLogger
import mu.KotlinLogging
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
        singleOf(::kLogger)
        singleOf(::jwtConfig)
        singleOf(::json)
        singleOf(::CheckUsernameUseCase)
        singleOf(::CheckPasswordUseCase)
    }

val Application.dataModule
    get() = dataModule(
        url = environment.config.property(path = "db.url").getString(),
        driver = org.h2.Driver::class,
    )

private fun Application.kLogger(): KLogger = KotlinLogging.logger(log)

private fun json(): Json = Json(domainJson) {}

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val realm: String,
)

private fun Application.jwtConfig(): JwtConfig = JwtConfig(
    secret = environment.config.property(path = "jwt.secret").getString(),
    issuer = environment.config.property(path = "jwt.issuer").getString(),
    realm = environment.config.property(path = "jwt.realm").getString(),
)
