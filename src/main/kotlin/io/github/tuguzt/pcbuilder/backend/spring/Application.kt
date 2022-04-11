package io.github.tuguzt.pcbuilder.backend.spring

import io.github.tuguzt.pcbuilder.backend.spring.di.appModule
import io.github.tuguzt.pcbuilder.backend.spring.di.networkModule
import mu.KotlinLogging
import org.koin.core.context.startKoin
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 * The main class of the server application.
 */
@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration::class)
class Application

private val koinLogger = KotlinLogging.logger("koin")

/**
 * Entry point of the server application.
 */
fun main(vararg args: String) {
    startKoin {
        kLogger(koinLogger)
        modules(appModule, networkModule)
    }
    runApplication<Application>(*args)
}
