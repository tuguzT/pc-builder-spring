@file:Suppress("SameParameterValue")

package io.github.tuguzt.pcbuilder.backend.spring.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.tuguzt.pcbuilder.backend.spring.retrofit.OctopartAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create

const val octopartBaseUrl = "https://octopart.com/api/"

val octopartModule = module {
    @OptIn(ExperimentalSerializationApi::class)
    single(named("json-converter-factory")) {
        val json: Json = get()
        json.asConverterFactory(MediaType.get("application/json"))
    }

    single(named("retrofit-octopart")) {
        retrofit(octopartBaseUrl, get(named("json-converter-factory")))
    }
    single { octopartAPI(get(named("retrofit-octopart"))) }
}

private fun retrofit(baseUrl: String, converterFactory: Converter.Factory): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .build()

private fun octopartAPI(retrofit: Retrofit): OctopartAPI = retrofit.create()
