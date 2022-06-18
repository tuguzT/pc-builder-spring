package io.github.tuguzt.pcbuilder.backend.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.tuguzt.pcbuilder.backend.plugins.koin.JwtConfig
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.Error
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import mu.KLogger
import org.koin.ktor.ext.inject

/**
 * Configures security of the application.
 */
fun Application.configureSecurity() {
    val logger: KLogger by inject()
    val jwtConfig: JwtConfig by inject()

    authentication {
        jwt(name = "auth-jwt") {
            realm = jwtConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )
            validate { credential ->
                val userRepository: UserRepository<Nothing?> by inject()

                val id = credential.payload.subject.let(::NanoId)
                val user = when (val result = userRepository.readById(id)) {
                    is Result.Error -> throw checkNotNull(result.cause)
                    is Result.Success -> result.data
                }
                if (user != null) JWTPrincipal(credential.payload) else null
            }
            challenge { defaultScheme, _ ->
                logger.error { "JWT authentication with default scheme $defaultScheme failed" }
                val error = Error(message = "JWT authentication failed", at = Clock.System.now())
                call.respond(HttpStatusCode.Unauthorized, error)
            }
        }
    }
}
