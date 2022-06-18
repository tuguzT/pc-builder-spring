package io.github.tuguzt.pcbuilder.backend.exceptions

/**
 * Exception is thrown when invalid credentials was provided by the user.
 */
class BadCredentialsException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause)
