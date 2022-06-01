package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserNamePasswordEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [JpaRepository] which contains data of type [UserNamePasswordEntity].
 */
@Repository
interface UserNamePasswordRepository : JpaRepository<UserNamePasswordEntity, NanoId> {
    fun findByUsername(username: String): UserNamePasswordEntity?
}
