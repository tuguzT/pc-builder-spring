package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.UserOAuth2Data
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserOAuth2Entity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserOAuth2Repository
import io.github.tuguzt.pcbuilder.backend.spring.service.UserOAuth2Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserOAuth2ServiceImpl(private val repository: UserOAuth2Repository) : UserOAuth2Service {
    override suspend fun save(item: UserOAuth2Data): UserOAuth2Data =
        withContext(Dispatchers.IO) { repository.save(item.toEntity()) }.toData()

    override suspend fun delete(item: UserOAuth2Data) =
        withContext(Dispatchers.IO) { repository.delete(item.toEntity()) }

    override suspend fun getAll(): List<UserOAuth2Data> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(UserOAuth2Entity::toData)

    override suspend fun findById(id: String): UserOAuth2Data? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }?.toData()

    override suspend fun deleteById(id: String) =
        withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: UserOAuth2Data): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }

    override suspend fun findByAccessToken(accessToken: String): UserOAuth2Data? =
        withContext(Dispatchers.IO) { repository.findByAccessToken(accessToken) }?.toData()
}
