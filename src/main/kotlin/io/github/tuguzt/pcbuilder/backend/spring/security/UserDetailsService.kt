package io.github.tuguzt.pcbuilder.backend.spring.security

import io.github.tuguzt.pcbuilder.backend.spring.service.UserNamePasswordService
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userNamePasswordService: UserNamePasswordService,
    private val userService: UserService,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return when (val user = runBlocking { userNamePasswordService.findByUsername(username) }) {
            null -> {
                val googleUser = runBlocking { userService.findByUsername(username) }
                    ?: throw UsernameNotFoundException("User $username not found")
                User(googleUser.username, "", setOf(SimpleGrantedAuthority("${googleUser.role}")))
            }
            else -> User(user.username, user.password, setOf(SimpleGrantedAuthority("${user.role}")))
        }
    }
}
