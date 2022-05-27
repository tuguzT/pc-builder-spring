package io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions

import org.springframework.security.core.AuthenticationException

class OAuth2FailedException(override val message: String) : AuthenticationException(message)
