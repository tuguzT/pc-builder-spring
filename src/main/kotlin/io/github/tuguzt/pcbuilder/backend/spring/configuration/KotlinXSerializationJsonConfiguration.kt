package io.github.tuguzt.pcbuilder.backend.spring.configuration

import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class KotlinXSerializationJsonConfiguration : WebMvcConfigurer, KoinComponent {
    private val json: Json by inject()

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val converter = KotlinSerializationJsonHttpMessageConverter(json)
        converters.forEachIndexed { index, httpMessageConverter ->
            if (httpMessageConverter is KotlinSerializationJsonHttpMessageConverter) {
                converters[index] = converter
                return
            }
        }
    }
}
