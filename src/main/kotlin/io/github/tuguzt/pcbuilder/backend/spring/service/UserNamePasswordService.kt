package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.domain.model.NanoId

interface UserNamePasswordService : RepositoryService<NanoId, UserNamePasswordData> {
    suspend fun findByUsername(username: String): UserNamePasswordData?
}
