package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.ComponentData

/**
 * Basic interface for service of [ComponentData].
 */
interface ComponentService : RepositoryService<ComponentData, String>
