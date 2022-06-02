package io.github.tuguzt.pcbuilder.backend.spring.repository.component

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.component.motherboard.MotherboardFormFactorEntity
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.MotherboardFormFactor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MotherboardFormFactorRepository : JpaRepository<MotherboardFormFactorEntity, MotherboardFormFactor>
