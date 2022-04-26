package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userNamePasswordRepository: UserNamePasswordRepository,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val users = listOf(
            kotlin.run {
                val username = "tuguzT"
                val password = "S6iwekjbbi_92,!"
                val email = "timurka.tugushev@gmail.com"
                require(checkUsername(username) && checkPassword(password))
                UserNamePasswordEntity(
                    role = UserRole.Administrator,
                    email = email,
                    imageUri = null,
                    username = username,
                    password = passwordEncoder.encode(password),
                )
            },
            kotlin.run {
                val username = "dr3am_b3ast"
                val password = "Y873lin)*odjv"
                require(checkUsername(username) && checkPassword(password))
                UserNamePasswordEntity(
                    role = UserRole.Moderator,
                    email = null,
                    imageUri = null,
                    username = username,
                    password = passwordEncoder.encode(password),
                )
            },
        )
        userNamePasswordRepository.saveAll(users)
    }
}
