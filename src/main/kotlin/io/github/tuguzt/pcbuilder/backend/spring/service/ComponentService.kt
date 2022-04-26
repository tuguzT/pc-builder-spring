package io.github.tuguzt.pcbuilder.backend.spring.service

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.ComponentEntity

/**
 * Basic interface for service of [ComponentEntity].
 */
interface ComponentService : RepositoryService<ComponentEntity, String>
