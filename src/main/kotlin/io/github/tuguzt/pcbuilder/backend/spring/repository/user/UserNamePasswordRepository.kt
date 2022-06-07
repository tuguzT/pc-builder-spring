package io.github.tuguzt.pcbuilder.backend.spring.repository.user

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserNamePasswordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [JpaRepository] which contains data of type [UserNamePasswordEntity].
 */
@Repository
interface UserNamePasswordRepository : JpaRepository<UserNamePasswordEntity, String> {
    fun findByUsername(username: String): UserNamePasswordEntity?
}
