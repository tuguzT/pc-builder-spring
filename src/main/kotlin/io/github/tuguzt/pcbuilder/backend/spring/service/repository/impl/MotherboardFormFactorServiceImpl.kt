package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.MotherboardFormFactorRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.MotherboardFormFactorService
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MotherboardFormFactorServiceImpl(private val repository: MotherboardFormFactorRepository) :
    MotherboardFormFactorService {

    override suspend fun getAll(): List<MotherboardFormFactorEntity> =
        withContext(Dispatchers.IO) { repository.findAll() }

    override suspend fun getAll(pageable: Pageable): List<MotherboardFormFactorEntity> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content

    override suspend fun save(item: MotherboardFormFactorEntity): MotherboardFormFactorEntity =
        withContext(Dispatchers.IO) { repository.save(item) }

    override suspend fun delete(item: MotherboardFormFactorEntity) =
        withContext(Dispatchers.IO) { repository.delete(item) }

    override suspend fun findById(id: MotherboardFormFactor): MotherboardFormFactorEntity? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id) }

    override suspend fun deleteById(id: MotherboardFormFactor) =
        withContext(Dispatchers.IO) { repository.deleteById(id) }

    override suspend fun exists(item: MotherboardFormFactorEntity): Boolean =
        withContext(Dispatchers.IO) { repository.existsById(item.id) }
}
