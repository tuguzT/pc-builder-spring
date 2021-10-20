package io.github.tuguzt.pcbuilder.backend.spring

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
    runApplication<Application>(*args)
}
