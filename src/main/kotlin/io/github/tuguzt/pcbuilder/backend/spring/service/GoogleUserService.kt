package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.GoogleUserData
import io.github.tuguzt.pcbuilder.domain.model.NanoId

interface GoogleUserService : RepositoryService<NanoId, GoogleUserData> {
    suspend fun findByGoogleId(googleId: String): GoogleUserData?
}
