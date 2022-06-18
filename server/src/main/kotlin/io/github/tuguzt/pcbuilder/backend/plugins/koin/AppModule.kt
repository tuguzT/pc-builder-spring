package io.github.tuguzt.pcbuilder.backend.plugins.koin

import io.github.tuguzt.pcbuilder.domain.usecase.CheckPasswordUseCase
import io.github.tuguzt.pcbuilder.domain.usecase.CheckUsernameUseCase
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import mu.KLogger
import mu.toKLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import io.github.tuguzt.pcbuilder.domain.model.serialization.json as domainJson

/**
 * General application module.
 */
val Application.appModule: Module
    get() = module {
        singleOf(::kLogger)
        singleOf(::json)
        singleOf(::CheckUsernameUseCase)
        singleOf(::CheckPasswordUseCase)

        factoryOf(::jwtConfig)
    }

private fun Application.kLogger(): KLogger = log.toKLogger()

private fun json(): Json = Json(domainJson) {}

private fun Application.jwtConfig(): JwtConfig = JwtConfig(
    secret = environment.config.property(path = "jwt.secret").getString(),
    issuer = environment.config.property(path = "jwt.issuer").getString(),
    realm = environment.config.property(path = "jwt.realm").getString(),
)
