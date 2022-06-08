package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.MotherboardData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.springframework.data.domain.Pageable

interface MotherboardService {
    suspend fun getAll(currentUser: UserData): List<MotherboardData>

    suspend fun save(item: MotherboardData, currentUser: UserData?): MotherboardData

    suspend fun delete(item: MotherboardData)

    suspend fun findById(id: NanoId, currentUser: UserData): MotherboardData?

    suspend fun deleteById(id: NanoId)

    suspend fun exists(item: MotherboardData): Boolean

    suspend fun getAll(pageable: Pageable, currentUser: UserData): List<MotherboardData>

    suspend fun findByName(name: String, currentUser: UserData?): MotherboardData?
}
