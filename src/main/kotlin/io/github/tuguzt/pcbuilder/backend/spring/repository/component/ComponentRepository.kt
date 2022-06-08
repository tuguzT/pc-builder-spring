package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.ComponentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [JpaRepository] which contains data of type [ComponentEntity].
 */
@Repository
interface ComponentRepository : JpaRepository<ComponentEntity, String> {
    fun findByName(name: String): ComponentEntity?
}
