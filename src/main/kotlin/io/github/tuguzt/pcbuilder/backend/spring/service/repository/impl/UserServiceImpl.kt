package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.user.UserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.UserService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override suspend fun getAll(): List<UserData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(UserEntity::toData)

    override suspend fun getAll(pageable: Pageable): List<UserData> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map(UserEntity::toData)

    override suspend fun save(item: UserData): UserData =
        withContext(Dispatchers.IO) {
            val favoriteComponents = repository.findByIdOrNull(item.id.toString())?.favoriteComponents ?: setOf()
            repository.save(item.toEntity(favoriteComponents))
        }.toData()

    override suspend fun delete(item: UserData) =
        withContext(Dispatchers.IO) {
            val favoriteComponents = repository.findByIdOrNull(item.id.toString())?.favoriteComponents ?: setOf()
            repository.delete(item.toEntity(favoriteComponents))
        }

    override suspend fun findById(id: NanoId): UserData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id.toString()) }?.toData()

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id.toString()) }

    override suspend fun exists(item: UserData): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id.toString()) }

    override suspend fun findByUsername(username: String): UserData? =
        withContext(Dispatchers.IO) { repository.findByUsername(username) }?.toData()
}
