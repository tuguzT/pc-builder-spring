package io.github.tuguzt.pcbuilder.backend.exceptions

/**
 * Exception is thrown when the user was not found.
 */
class UserNotFoundException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause)
