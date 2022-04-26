package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData

interface UserNamePasswordService : RepositoryService<UserNamePasswordData, String> {
    suspend fun findByUsername(username: String): UserNamePasswordData?
}
