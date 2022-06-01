package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.ManufacturerData

interface ManufacturerService : RepositoryService<NanoId, ManufacturerData> {
    suspend fun findByName(name: String): ManufacturerData?
}