package io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies

object Ktor {
    const val version = "2.0.2"

    object Server {
        const val core = "io.ktor:ktor-server-core-jvm:$version"
        const val engineNetty = "io.ktor:ktor-server-netty-jvm:$version"

        object Http {
            const val contentNegitiation = "io.ktor:ktor-server-content-negotiation-jvm:$version"
        }

        object Security {
            const val auth = "io.ktor:ktor-server-auth-jvm:$version"
            const val authJwt = "io.ktor:ktor-server-auth-jwt-jvm:$version"
        }

        object Serialization {
            const val kotlinX = "io.ktor:ktor-serialization-kotlinx-json-jvm:$version"
        }
    }
}
