package io.github.tuguzt.pcbuilder.backend.spring.security

import io.github.tuguzt.pcbuilder.backend.spring.repository.UserNamePasswordRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val repository: UserNamePasswordRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUsername(username)
            ?: throw UsernameNotFoundException("User with username $username not found")
        return User(user.username, user.password, setOf(SimpleGrantedAuthority(user.role.toString())))
    }
}
