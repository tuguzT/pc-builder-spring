package io.github.tuguzt.pcbuilder.backend.data.repository

import io.github.tuguzt.pcbuilder.domain.Result

/**
 * Repository for user password hashing.
 */
interface PasswordHashRepository {
    fun hash(password: String): Result<String, Nothing?>

    fun verify(hash: String, password: String): Result<Boolean, Nothing?>
}
