package io.github.tuguzt.pcbuilder.backend.data.datasource

import io.github.tuguzt.pcbuilder.domain.Result

interface PasswordHashDataSource {
    fun hash(password: String): Result<String, Nothing?>

    fun verify(hash: String, password: String): Result<Boolean, Nothing?>
}
