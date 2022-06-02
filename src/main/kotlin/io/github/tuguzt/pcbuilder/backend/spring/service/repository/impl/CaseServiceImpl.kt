package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.CaseRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.CaseService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CaseServiceImpl(private val repository: CaseRepository) : CaseService {
    override suspend fun save(item: CaseData): CaseData =
        withContext(Dispatchers.IO) { repository.save(item.toEntity()) }.toData()

    override suspend fun delete(item: CaseData) =
        withContext(Dispatchers.IO) { repository.delete(item.toEntity()) }

    override suspend fun findByName(name: String): CaseData? =
        withContext(Dispatchers.IO) { repository.findByName(name) }?.toData()

    override suspend fun getAll(): List<CaseData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(CaseEntity::toData)

    override suspend fun getAll(pageable: Pageable): List<CaseData> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map(CaseEntity::toData)

    override suspend fun findById(id: NanoId): CaseData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }?.toData()

    override suspend fun deleteById(id: NanoId) = withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: CaseData) =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }
}
