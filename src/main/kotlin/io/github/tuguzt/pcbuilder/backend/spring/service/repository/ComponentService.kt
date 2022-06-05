package io.github.tuguzt.pcbuilder.backend.spring.service.repository

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.data.ComponentData

/**
 * Basic interface for service of [ComponentData].
 */
interface ComponentService : RepositoryService<NanoId, ComponentData> {
    suspend fun findByName(name: String): ComponentData?
}
