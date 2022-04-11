package io.github.tuguzt.pcbuilder.backend.spring

import io.github.tuguzt.pcbuilder.backend.spring.di.appModule
import org.koin.core.context.startKoin
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
        printLogger()
        modules(appModule)
    }
    runApplication<Application>(*args)
}
