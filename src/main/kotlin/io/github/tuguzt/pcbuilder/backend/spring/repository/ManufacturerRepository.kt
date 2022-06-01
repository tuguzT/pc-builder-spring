package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.ManufacturerEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ManufacturerRepository : JpaRepository<ManufacturerEntity, NanoId> {
    fun findByName(name: String): ManufacturerEntity?
}
