package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.cases.data.CaseData

interface CaseService : RepositoryService<NanoId, CaseData> {
    suspend fun findByName(name: String): CaseData?
}
