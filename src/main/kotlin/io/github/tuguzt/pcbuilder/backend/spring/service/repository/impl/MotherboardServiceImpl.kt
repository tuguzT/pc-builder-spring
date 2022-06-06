package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.MotherboardRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.MotherboardService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MotherboardServiceImpl(private val repository: MotherboardRepository) : MotherboardService {
    override suspend fun getAll(currentUser: UserData): List<MotherboardData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map { it.toData(currentUser) }

    override suspend fun save(item: MotherboardData, currentUser: UserData?): MotherboardData =
        withContext(Dispatchers.IO) {
            val favorites = repository.findByIdOrNull(item.id.toString())?.favorites ?: setOf()
            repository.save(item.toEntity(favorites))
        }.toData(currentUser)

    override suspend fun delete(item: MotherboardData) =
        withContext(Dispatchers.IO) {
            val favorites = repository.findByIdOrNull(item.id.toString())?.favorites ?: setOf()
            repository.delete(item.toEntity(favorites))
        }

    override suspend fun findById(id: NanoId, currentUser: UserData): MotherboardData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id.toString()) }?.toData(currentUser)

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id.toString()) }

    override suspend fun exists(item: MotherboardData): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id.toString()) }

    override suspend fun getAll(pageable: Pageable, currentUser: UserData): List<MotherboardData> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map { it.toData(currentUser) }

    override suspend fun findByName(name: String, currentUser: UserData?): MotherboardData? =
        withContext(Dispatchers.IO) { repository.findByName(name) }?.toData(currentUser)
}
