package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MotherboardRepository : JpaRepository<MotherboardEntity, String> {
    fun findByName(name: String): MotherboardEntity?
}
