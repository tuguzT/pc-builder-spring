package io.github.tuguzt.pcbuilder.backend.spring.service.repository.impl

import io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions.NotFoundException
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.build.BuildEntity
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.toData
import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import io.github.tuguzt.pcbuilder.backend.spring.repository.build.BuildRepository
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.CaseRepository
import io.github.tuguzt.pcbuilder.backend.spring.repository.component.MotherboardRepository
import io.github.tuguzt.pcbuilder.backend.spring.repository.user.UserRepository
import io.github.tuguzt.pcbuilder.backend.spring.service.repository.BuildService
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BuildServiceImpl(
    private val userRepository: UserRepository,
    private val buildRepository: BuildRepository,
    private val caseRepository: CaseRepository,
    private val motherboardRepository: MotherboardRepository,
) : BuildService {

    override suspend fun getAll(currentUser: UserData): List<BuildData> =
        withContext(Dispatchers.IO) { buildRepository.findAll() }.map { it.toData() }

    override suspend fun save(item: BuildData, currentUser: UserData?): BuildData = withContext(Dispatchers.IO) {
        val user = userRepository.findByIdOrNull(currentUser?.id?.toString()) ?: throw NotFoundException()
        buildRepository.save(item.toEntity(user)).toData()
    }

    override suspend fun delete(item: BuildData): Unit =
        withContext(Dispatchers.IO) { buildRepository.deleteById(item.id.toString()) }

    override suspend fun findById(id: NanoId): BuildData? =
        withContext(Dispatchers.IO) { buildRepository.findByIdOrNull(id.toString()) }?.toData()

    override suspend fun deleteById(id: NanoId): Unit =
        withContext(Dispatchers.IO) { buildRepository.deleteById(id.toString()) }

    override suspend fun exists(item: BuildData): Boolean =
        withContext(Dispatchers.IO) { buildRepository.existsById(item.id.toString()) }

    override suspend fun getAll(pageable: Pageable, currentUser: UserData): List<BuildData> =
        withContext(Dispatchers.IO) { buildRepository.findAll(pageable) }.content.map { it.toData() }

    override suspend fun findByName(name: String): BuildData? =
        withContext(Dispatchers.IO) { buildRepository.findByName(name) }?.toData()

    private fun BuildData.toEntity(currentUser: UserEntity): BuildEntity {
        return BuildEntity(
            id = id.toString(),
            name = name,
            user = currentUser,
            case = case?.id?.toString()?.let { caseRepository.findByIdOrNull(it) },
            motherboard = motherboard?.id?.toString()?.let { motherboardRepository.findByIdOrNull(it) },
        )
    }
}
