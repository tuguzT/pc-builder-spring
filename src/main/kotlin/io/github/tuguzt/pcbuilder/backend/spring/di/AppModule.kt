package io.github.tuguzt.pcbuilder.backend.spring.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single { json() }
}

private fun json() = Json { ignoreUnknownKeys = true }
