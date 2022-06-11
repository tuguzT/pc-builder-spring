package io.github.tuguzt.pcbuilder.backend.di

import io.github.tuguzt.pcbuilder.backend.data.di.dataModule
import org.h2.Driver

val dataModule = dataModule(
    url = "jdbc:h2:mem:test",
    driver = requireNotNull(Driver::class.qualifiedName),
    username = "username",
    password = "password",
)
