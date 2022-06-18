package io.github.tuguzt.pcbuilder.backend.plugins.koin

/**
 * JWT configuration data from the `application.conf` file.
 */
data class JwtConfig(
    val secret: String,
    val issuer: String,
    val realm: String,
)
