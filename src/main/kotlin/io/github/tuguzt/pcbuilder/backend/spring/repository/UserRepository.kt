package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [JpaRepository] which contains data of type [UserEntity].
 */
@Repository
interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}
