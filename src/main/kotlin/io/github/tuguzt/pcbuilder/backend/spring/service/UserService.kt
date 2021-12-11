package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity

interface UserService {
    suspend fun save(entity: UserEntity): UserEntity

    suspend fun getAll(): List<UserEntity>

    suspend fun findById(id: String): UserEntity?

    suspend fun findByUsername(username: String): UserEntity?
}
