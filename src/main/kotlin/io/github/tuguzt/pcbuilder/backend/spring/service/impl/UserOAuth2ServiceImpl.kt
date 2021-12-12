package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Entity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserOAuth2Repository
import io.github.tuguzt.pcbuilder.backend.spring.service.UserOAuth2Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserOAuth2ServiceImpl(private val repository: UserOAuth2Repository) : UserOAuth2Service {
    override suspend fun save(entity: UserOAuth2Entity) = withContext(Dispatchers.IO) { repository.save(entity) }

    override suspend fun getAll(): List<UserOAuth2Entity> = withContext(Dispatchers.IO) { repository.findAll() }

    override suspend fun findById(id: String) = withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }

    override suspend fun findByAccessToken(accessToken: String) =
        withContext(Dispatchers.IO) { repository.findByAccessToken(accessToken) }
}
