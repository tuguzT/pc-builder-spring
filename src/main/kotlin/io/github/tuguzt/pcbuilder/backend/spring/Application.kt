package io.github.tuguzt.pcbuilder.backend.spring

import io.github.tuguzt.pcbuilder.backend.spring.di.appModule
import io.github.tuguzt.pcbuilder.backend.spring.di.octopartModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The main class of the server application.
 */
@SpringBootApplication
class Application

/**
 * Entry point of the server application.
 */
fun main(args: Array<String>) {
    startKoin {
        printLogger(Level.ERROR)
        modules(appModule, octopartModule)
    }
    runApplication<Application>(*args)
}
