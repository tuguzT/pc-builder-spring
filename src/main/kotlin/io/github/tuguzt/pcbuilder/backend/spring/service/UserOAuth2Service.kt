package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Data

interface UserOAuth2Service : RepositoryService<UserOAuth2Data, String> {
    suspend fun findByAccessToken(accessToken: String): UserOAuth2Data?
}
