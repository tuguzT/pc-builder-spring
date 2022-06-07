package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.springframework.data.domain.Pageable

interface BuildService {
    suspend fun getAll(currentUser: UserData): List<BuildData>

    suspend fun save(item: BuildData, currentUser: UserData?): BuildData

    suspend fun delete(item: BuildData)

    suspend fun findById(id: NanoId): BuildData?

    suspend fun deleteById(id: NanoId)

    suspend fun exists(item: BuildData): Boolean

    suspend fun getAll(pageable: Pageable, currentUser: UserData): List<BuildData>

    suspend fun findByName(name: String): BuildData?
}
