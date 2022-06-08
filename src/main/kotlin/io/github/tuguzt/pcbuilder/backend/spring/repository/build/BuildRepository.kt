package io.github.tuguzt.pcbuilder.backend.spring.repository.build

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.build.BuildEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BuildRepository : JpaRepository<BuildEntity, String> {
    fun findByName(name: String): BuildEntity?
}
