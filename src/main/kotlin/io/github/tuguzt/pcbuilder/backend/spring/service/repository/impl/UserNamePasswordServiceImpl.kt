package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.UserNamePasswordData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.user.UserNamePasswordRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.UserNamePasswordService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserNamePasswordServiceImpl(private val repository: UserNamePasswordRepository) : UserNamePasswordService {
    override suspend fun save(item: UserNamePasswordData): UserNamePasswordData =
        withContext(Dispatchers.IO) {
            val favoriteComponents = repository.findByIdOrNull(item.id.toString())?.favoriteComponents ?: setOf()
            repository.save(item.toEntity(favoriteComponents))
        }.toData()

    override suspend fun delete(item: UserNamePasswordData) =
        withContext(Dispatchers.IO) {
            val favoriteComponents = repository.findByIdOrNull(item.id.toString())?.favoriteComponents ?: setOf()
            repository.delete(item.toEntity(favoriteComponents))
        }

    override suspend fun getAll(): List<UserNamePasswordData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(UserNamePasswordEntity::toData)

    override suspend fun getAll(pageable: Pageable): List<UserNamePasswordData> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map(UserNamePasswordEntity::toData)

    override suspend fun findById(id: NanoId): UserNamePasswordData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id.toString()) }?.toData()

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id.toString()) }

    override suspend fun exists(item: UserNamePasswordData): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id.toString()) }

    override suspend fun findByUsername(username: String): UserNamePasswordData? =
        withContext(Dispatchers.IO) { repository.findByUsername(username) }?.toData()
}
