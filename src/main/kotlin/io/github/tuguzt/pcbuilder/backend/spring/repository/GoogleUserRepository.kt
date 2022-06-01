package io.github.tuguzt.pcbuilder.backend.spring.repository

import io.github.tuguzt.pcbuilder.backend.spring.model.entity.user.GoogleUserEntity
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * [JpaRepository] which contains data of type [GoogleUserEntity].
 */
@Repository
interface GoogleUserRepository : JpaRepository<GoogleUserEntity, NanoId> {
    fun findByGoogleId(googleId: String): GoogleUserEntity?
}
