package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.CaseData
import io.github.tuguzt.pcbuilder.domain.model.user.data.UserData
import org.springframework.data.domain.Pageable

interface CaseService {
    suspend fun getAll(currentUser: UserData): List<CaseData>

    suspend fun save(item: CaseData, currentUser: UserData?): CaseData

    suspend fun delete(item: CaseData)

    suspend fun findById(id: NanoId, currentUser: UserData): CaseData?

    suspend fun deleteById(id: NanoId)

    suspend fun exists(item: CaseData): Boolean

    suspend fun getAll(pageable: Pageable, currentUser: UserData): List<CaseData>

    suspend fun findByName(name: String, currentUser: UserData?): CaseData?
}
