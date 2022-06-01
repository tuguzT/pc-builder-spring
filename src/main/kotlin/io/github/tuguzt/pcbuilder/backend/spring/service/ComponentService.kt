package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.component.ComponentData

/**
 * Basic interface for service of [ComponentData].
 */
interface ComponentService : RepositoryService<NanoId, ComponentData>
