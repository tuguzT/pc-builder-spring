package io.github.tuguzt.pcbuilder.backend.di

import io.github.tuguzt.pcbuilder.backend.data.Database
import org.h2.Driver
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val dataModule = module {
    single { database() } withOptions {
        createdAtStart()
    }
}

private fun database(): Database {
    val url = "jdbc:h2:mem:test"
    val driver = requireNotNull(Driver::class.qualifiedName)
    val username = "username"
    val password = "password"
    return Database(url, driver, username, password)
}
