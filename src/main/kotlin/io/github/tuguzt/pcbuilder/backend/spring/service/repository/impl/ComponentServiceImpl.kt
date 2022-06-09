package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.toEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.ComponentRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ComponentService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.ManufacturerService
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.MotherboardFormFactorService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.component.data.PolymorphicComponent
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Implementation of the [ComponentService] interface.
 */
@Service
class ComponentServiceImpl(
    private val repository: ComponentRepository,
    private val manufacturerService: ManufacturerService,
    private val motherboardFormFactorService: MotherboardFormFactorService,
) : ComponentService {

    override suspend fun save(item: PolymorphicComponent, currentUser: UserData): PolymorphicComponent =
        withContext(Dispatchers.IO) {
            val manufacturerEntity = manufacturerService.save(item.manufacturer)
            when (item) {
                is CaseData -> item.motherboardFormFactors.map(MotherboardFormFactor::toEntity).forEach {
                    motherboardFormFactorService.save(it)
                }
                is MotherboardData -> motherboardFormFactorService.save(item.formFactor.toEntity())
                else -> TODO()
            }
            val favorites = repository.findByIdOrNull(item.id.toString())?.favorites ?: setOf()
            val toEntity = item.toEntity(favorites, manufacturerEntity.toEntity())
            repository.save(toEntity)
        }.toData(currentUser)

    override suspend fun delete(item: PolymorphicComponent) =
        withContext(Dispatchers.IO) {
            val favorites = repository.findByIdOrNull(item.id.toString())?.favorites ?: setOf()
            repository.delete(item.toEntity(favorites))
        }

    override suspend fun findByName(name: String, currentUser: UserData): PolymorphicComponent? =
        withContext(Dispatchers.IO) { repository.findByName(name) }?.toData(currentUser)

    override suspend fun getAll(currentUser: UserData): List<PolymorphicComponent> =
        withContext(Dispatchers.IO) { repository.findAll() }.map { it.toData(currentUser) }

    override suspend fun getAll(pageable: Pageable, currentUser: UserData): List<PolymorphicComponent> =
        withContext(Dispatchers.IO) { repository.findAll(pageable) }.content.map { it.toData(currentUser) }

    override suspend fun findById(id: NanoId, currentUser: UserData): PolymorphicComponent? =
        withContext(Dispatchers.IO) { repository.findByIdOrNull(id.toString()) }?.toData(currentUser)

    override suspend fun deleteById(id: NanoId) =
        withContext(Dispatchers.IO) { repository.deleteById(id.toString()) }

    override suspend fun exists(item: PolymorphicComponent) =
        withContext(Dispatchers.IO) { repository.existsById(item.id.toString()) }
}
