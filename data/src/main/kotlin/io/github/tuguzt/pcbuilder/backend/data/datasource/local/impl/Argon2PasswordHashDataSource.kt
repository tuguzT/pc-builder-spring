package io.github.tuguzt.pcbuilder.backend.data.datasource.local.impl

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Helper
import io.github.tuguzt.pcbuilder.backend.data.datasource.PasswordHashDataSource
import io.github.tuguzt.pcbuilder.domain.Result
import io.github.tuguzt.pcbuilder.domain.error
import io.github.tuguzt.pcbuilder.domain.success

internal class Argon2PasswordHashDataSource(private val argon2: Argon2) : PasswordHashDataSource {
    override fun hash(password: String): Result<String, Nothing?> {
        val charArray = password.toCharArray()
        val memory = 65536
        val parallelism = 1
        val iterations = Argon2Helper.findIterations(argon2, 1000, memory, parallelism)
        return try {
            val hash = argon2.hash(iterations, memory, parallelism, charArray)
            success(hash)
        } catch (cause: Throwable) {
            error(error = null, cause = cause)
        } finally {
            argon2.wipeArray(charArray)
        }
    }

    override fun verify(hash: String, password: String): Result<Boolean, Nothing?> {
        val charArray = password.toCharArray()
        return try {
            val matches = argon2.verify(hash, charArray)
            success(matches)
        } catch (cause: Throwable) {
            error(error = null, cause = cause)
        } finally {
            argon2.wipeArray(charArray)
        }
    }
}
