package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordEntity

interface UserNamePasswordService : EntityService<UserNamePasswordEntity, String> {
    suspend fun findByUsername(username: String): UserNamePasswordEntity?
}
