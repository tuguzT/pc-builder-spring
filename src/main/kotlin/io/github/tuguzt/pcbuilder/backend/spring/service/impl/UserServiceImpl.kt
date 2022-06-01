package io.github.tuguzt.pcbuilder.backend.spring.service.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.UserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.UserService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override suspend fun getAll(): List<UserData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(UserEntity::toData)

    override suspend fun save(item: UserData): UserData =
        withContext(Dispatchers.IO) { repository.save(item.toEntity()) }.toData()

    override suspend fun delete(item: UserData) =
        withContext(Dispatchers.IO) { repository.delete(item.toEntity()) }

    override suspend fun findById(id: NanoId): UserData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }?.toData()

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: UserData): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }

    override suspend fun findByUsername(username: String): UserData? =
        withContext(Dispatchers.IO) { repository.findByUsername(username) }?.toData()
}
