package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity

interface UserService : EntityService<UserEntity, String> {
    suspend fun findByEmail(email: String): UserEntity?
}
