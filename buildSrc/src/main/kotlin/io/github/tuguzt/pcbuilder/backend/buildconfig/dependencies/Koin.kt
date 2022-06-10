package io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies

object Koin {
    const val version = "3.2.0"

    const val core = "io.insert-koin:koin-core:$version"

    object Ktor {
        const val dependency = "io.insert-koin:koin-ktor:$version"
    }
}
