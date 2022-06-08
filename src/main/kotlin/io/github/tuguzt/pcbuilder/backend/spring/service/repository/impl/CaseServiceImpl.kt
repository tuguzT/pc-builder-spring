package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.CaseRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.CaseService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CaseServiceImpl(private val repository: CaseRepository) : CaseService {
    override suspend fun save(item: CaseData, currentUser: UserData?): CaseData =
        withContext(Dispatchers.IO) {
            val favorites = repository.findByIdOrNull(item.id.toString())?.favorites ?: setOf()
            repository.save(item.toEntity(favorites))
        }.toData(currentUser)

    override suspend fun delete(item: CaseData) =
        withContext(Dispatchers.IO) {
            val favorites = repository.findByIdOrNull(item.id.toString())?.favorites ?: setOf()
            repository.delete(item.toEntity(favorites))
        }

    override suspend fun findByName(name: String, currentUser: UserData?): CaseData? =
        withContext(Dispatchers.IO) { repository.findByName(name) }?.toData(currentUser)

    override suspend fun getAll(currentUser: UserData): List<CaseData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map { it.toData(currentUser) }

    override suspend fun getAll(pageable: Pageable, currentUser: UserData): List<CaseData> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map { it.toData(currentUser) }

    override suspend fun findById(id: NanoId, currentUser: UserData): CaseData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id.toString()) }?.toData(currentUser)

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id.toString()) }

    override suspend fun exists(item: CaseData) =
        withContext(Dispatchers.IO) { repository.existsById(item.id.toString()) }
}
