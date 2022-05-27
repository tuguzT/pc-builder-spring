package io.github.tuguzt.pcbuilder.backend.spring.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.github.tuguzt.pcbuilder.domain.interactor.serialization.json as domainJson

val appModule = module {
    single { json() }
}

private fun json() = Json(domainJson) {}
