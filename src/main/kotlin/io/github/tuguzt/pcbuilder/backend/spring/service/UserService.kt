package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserData

interface UserService : RepositoryService<UserData, String> {
    suspend fun findByEmail(email: String): UserData?
}
