package io.github.tuguzt.pcbuilder.backend.plugins.koin

import io.github.tuguzt.pcbuilder.backend.data.di.dataModule
import io.ktor.server.application.*
import org.koin.core.module.Module

/**
 * Data module of the application.
 */
val Application.dataModule: Module
    get() = dataModule(
        url = environment.config.property(path = "db.url").getString(),
        driver = org.h2.Driver::class,
    )
