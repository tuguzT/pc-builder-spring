package io.github.tuguzt.pcbuilder.backend.spring.repository.user

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
}
