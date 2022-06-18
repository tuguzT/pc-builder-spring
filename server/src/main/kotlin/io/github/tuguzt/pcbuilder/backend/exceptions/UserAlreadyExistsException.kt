package io.github.tuguzt.pcbuilder.backend.exceptions

/**
 * Exception is thrown when the user tries to register with existing username.
 */
class UserAlreadyExistsException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause)
