package io.github.tuguzt.pcbuilder.backend.spring

import io.github.tuguzt.pcbuilder.backend.spring.di.appModule
import io.github.tuguzt.pcbuilder.backend.spring.di.networkModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.logger.slf4jLogger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 * The main class of the server application.
 */
@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration::class)
class Application

/**
 * Entry point of the server application.
 */
fun main(vararg args: String) {
    startKoin {
        slf4jLogger(level = Level.DEBUG)
        modules(appModule, networkModule)
    }
    runApplication<Application>(*args)
}
