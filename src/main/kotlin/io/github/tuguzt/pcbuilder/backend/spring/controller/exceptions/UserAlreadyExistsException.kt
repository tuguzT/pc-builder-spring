package io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions

import org.springframework.security.core.AuthenticationException

class UserAlreadyExistsException(override val message: String?) : AuthenticationException(message)
