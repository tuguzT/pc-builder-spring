package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordEntity

interface UserNamePasswordService {
    suspend fun save(entity: UserNamePasswordEntity): UserNamePasswordEntity

    suspend fun getAll(): List<UserNamePasswordEntity>

    suspend fun findById(id: String): UserNamePasswordEntity?

    suspend fun findByUsername(username: String): UserNamePasswordEntity?
}
