package io.github.tuguzt.pcbuilder.backend.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.github.tuguzt.pcbuilder.domain.model.serialization.json as domainJson

val appModule = module {
    single { json() }
}

private fun json(): Json = Json(domainJson) {}
