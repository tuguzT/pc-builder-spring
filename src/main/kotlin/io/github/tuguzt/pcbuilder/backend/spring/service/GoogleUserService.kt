package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.GoogleUserData

interface GoogleUserService : RepositoryService<GoogleUserData, String> {
    suspend fun findByGoogleId(googleId: String): GoogleUserData?
}
