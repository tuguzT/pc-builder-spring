package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ManufacturerEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.ManufacturerRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ManufacturerService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ManufacturerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ManufacturerServiceImpl(private val repository: ManufacturerRepository) : ManufacturerService {
    override suspend fun getAll(): List<ManufacturerData> =
        withContext(Dispatchers.IO) { repository.findAll() }.map(ManufacturerEntity::toData)

    override suspend fun save(item: ManufacturerData): ManufacturerData =
        withContext(Dispatchers.IO) { repository.save(item.toEntity()) }.toData()

    override suspend fun delete(item: ManufacturerData): Unit =
        withContext(Dispatchers.IO) { repository.delete(item.toEntity()) }

    override suspend fun findById(id: NanoId): ManufacturerData? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }?.toData()

    override suspend fun deleteById(id: NanoId): Unit =
        withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: ManufacturerData): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }

    override suspend fun findByName(name: String): ManufacturerData? =
        withContext(Dispatchers.IO) { repository.findByName(name) }?.toData()
}
