package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Entity

interface UserOAuth2Service {
    suspend fun save(entity: UserOAuth2Entity): UserOAuth2Entity

    suspend fun getAll(): List<UserOAuth2Entity>

    suspend fun findById(id: String): UserOAuth2Entity?

    suspend fun findByAccessToken(accessToken: String): UserOAuth2Entity?
}
