package io.github.tuguzt.pcbuilder.backend.spring.di

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import org.koin.dsl.module

val networkModule = module {
    includes(appModule)

    single { GoogleNetHttpTransport.newTrustedTransport() as HttpTransport }
    single { GsonFactory.getDefaultInstance() as JsonFactory }
}
