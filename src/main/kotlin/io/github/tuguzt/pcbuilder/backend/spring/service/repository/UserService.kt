package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData

interface UserService : RepositoryService<NanoId, UserData> {
    suspend fun findByUsername(username: String): UserData?
}
