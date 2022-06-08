package io.github.tuguzt.pcbuilder.backend.spring.controller.exceptions

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.util.WebUtils

@ControllerAdvice
class RestResponseExceptionHandler {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @ExceptionHandler(
        BadCredentialsException::class,
        OAuth2FailedException::class,
        UsernameNotFoundException::class,
        UserAlreadyExistsException::class,
        NotFoundException::class,
    )
    final fun handleException(exception: Exception, request: WebRequest): ResponseEntity<RestApiError> {
        val headers = HttpHeaders()
        return when (exception) {
            is UserAlreadyExistsException -> handleUserAlreadyExists(exception, headers, request)
            is UsernameNotFoundException -> handleUsernameNotFound(exception, headers, request)
            is BadCredentialsException -> handleBadCredentials(exception, headers, request)
            is OAuth2FailedException -> handleOAuth2Failed(exception, headers, request)
            is NotFoundException -> handleNotFound(exception, headers, request)
            else -> handleInternal(exception, body = null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request)
        }
    }

    protected fun handleOAuth2Failed(
        exception: OAuth2FailedException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError(exception.message)
        return handleInternal(exception, error, headers, HttpStatus.BAD_REQUEST, request)
    }

    protected fun handleUserAlreadyExists(
        exception: UserAlreadyExistsException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("User already exists")
        return handleInternal(exception, error, headers, HttpStatus.BAD_REQUEST, request)
    }

    protected fun handleUsernameNotFound(
        exception: UsernameNotFoundException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("User not found")
        return handleInternal(exception, error, headers, HttpStatus.NOT_FOUND, request)
    }

    protected fun handleBadCredentials(
        exception: BadCredentialsException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("Bad credentials provided")
        return handleInternal(exception, error, headers, HttpStatus.UNAUTHORIZED, request)
    }

    protected fun handleNotFound(
        exception: NotFoundException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("Resource was not found")
        return handleInternal(exception, error, headers, HttpStatus.NOT_FOUND, request)
    }

    protected fun handleInternal(
        exception: Exception,
        body: RestApiError?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST)
        }
        val error = body ?: RestApiError("Unknown server error")
        logger.error(exception) { error.message }
        return ResponseEntity(error, headers, status)
    }
}
