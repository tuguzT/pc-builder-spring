package io.github.tuguzt.pcbuilder.backend.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.repository.user.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

/**
 * Configures security options of the application.
 */
fun Application.configureSecurity() {
    authentication {
        jwt(name = "auth-jwt") {
            val config = this@configureSecurity.jwtConfig()
            realm = config.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                val userRepository: UserRepository<Nothing?> by inject()

                val id = credential.payload.getClaim("userId").asString().let(::NanoId)
                val user = when (val result = userRepository.readById(id)) {
                    is Result.Error -> throw checkNotNull(result.cause)
                    is Result.Success -> result.data
                }
                if (user != null) JWTPrincipal(credential.payload) else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val realm: String,
)

fun Application.jwtConfig(): JwtConfig = JwtConfig(
    secret = environment.config.property("jwt.secret").getString(),
    issuer = environment.config.property("jwt.issuer").getString(),
    realm = environment.config.property("jwt.realm").getString(),
)
