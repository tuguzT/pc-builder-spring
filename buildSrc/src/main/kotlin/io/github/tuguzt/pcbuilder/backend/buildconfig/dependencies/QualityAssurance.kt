package io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies

import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Koin as KoinDep
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Ktor as KtorDep

object QualityAssurance {
    object UnitTest {
        object Ktor {
            object Server {
                const val dependency = "io.ktor:ktor-server-tests-jvm:${KtorDep.version}"
            }
        }

        object Koin {
            const val dependency = "io.insert-koin:koin-test:${KoinDep.version}"
        }
    }

    object Log {
        object Ktor {
            const val slf4jLogger = "io.insert-koin:koin-logger-slf4j:${KoinDep.version}"
        }

        object Logback {
            const val version = "1.2.11"
            const val dependency = "ch.qos.logback:logback-classic:$version"
        }

        object KotlinLogging {
            const val version = "2.1.21"
            const val dependency = "io.github.microutils:kotlin-logging-jvm:$version"
        }
    }
}
