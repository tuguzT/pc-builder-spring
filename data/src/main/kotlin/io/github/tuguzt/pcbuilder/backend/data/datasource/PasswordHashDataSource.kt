package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.domain.Result

interface PasswordHashDataSource {
    suspend fun hash(password: String): Result<String, Nothing?>

    suspend fun verify(hash: String, password: String): Result<Boolean, Nothing?>
}
