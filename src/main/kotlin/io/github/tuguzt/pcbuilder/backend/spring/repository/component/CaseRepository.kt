package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.cases.CaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CaseRepository : JpaRepository<CaseEntity, String> {
    fun findByName(name: String): CaseEntity?
}
