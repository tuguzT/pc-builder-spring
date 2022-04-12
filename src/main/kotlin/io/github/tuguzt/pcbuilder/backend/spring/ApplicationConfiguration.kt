package io.github.tuguzt.pcbuilder.backend.spring

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class ApplicationConfiguration(val oauth2: OAuth2) {
    data class OAuth2(val google: Google) {
        data class Google(
            val clientId: String,
            val tokenUri: String,
            val clientSecret: String,
        )
    }
}
