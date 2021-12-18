package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Entity

interface UserOAuth2Service : EntityService<UserOAuth2Entity, String> {
    suspend fun findByAccessToken(accessToken: String): UserOAuth2Entity?
}
