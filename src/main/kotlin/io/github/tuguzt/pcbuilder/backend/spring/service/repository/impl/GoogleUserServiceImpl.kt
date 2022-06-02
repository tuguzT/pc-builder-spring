package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.GoogleUserData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.user.GoogleUserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.GoogleUserService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GoogleUserServiceImpl(private val repository: GoogleUserRepository) : GoogleUserService {
    override suspend fun save(item: GoogleUserData): GoogleUserData =
        withContext(Dispatchers.IO) { repository.save(item.toEntity()) }.toData()

    override suspend fun delete(item: GoogleUserData): Unit =
        withContext(Dispatchers.IO) { repository.delete(item.toEntity()) }

    override suspend fun getAll(): List<GoogleUserData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(GoogleUserEntity::toData)

    override suspend fun getAll(pageable: Pageable): List<GoogleUserData> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map(GoogleUserEntity::toData)

    override suspend fun findById(id: NanoId): GoogleUserData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }?.toData()

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: GoogleUserData): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }

    override suspend fun findByGoogleId(googleId: String): GoogleUserData? = withContext(Dispatchers.IO) {
        repository.findByGoogleId(googleId)?.toData()
    }
}
