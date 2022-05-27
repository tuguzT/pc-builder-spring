package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.UserOAuth2Entity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [JpaRepository] which contains data of type [UserOAuth2Entity].
 */
@Repository
interface UserOAuth2Repository : JpaRepository<UserOAuth2Entity, String> {
    fun findByAccessToken(accessToken: String): UserOAuth2Entity?
}
