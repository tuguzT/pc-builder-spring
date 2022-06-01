package io.github.tuguzt.pcbuilder.backend.spring.configuration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
class CoroutineScopeConfiguration {
    @Bean
    fun scope(): CoroutineScope = CoroutineScope(Dispatchers.Default)
}
