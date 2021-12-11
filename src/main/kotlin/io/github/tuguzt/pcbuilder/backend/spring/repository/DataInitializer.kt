package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val userRepository: UserRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val users = listOf(
            UserEntity(email = null, imageUri = null, username = "tuguzT", password = "tugushev_timur"),
            UserEntity(email = null, imageUri = null, username = "damir", password = "damir_tugushev"),
        )
        userRepository.saveAll(users)
    }
}
