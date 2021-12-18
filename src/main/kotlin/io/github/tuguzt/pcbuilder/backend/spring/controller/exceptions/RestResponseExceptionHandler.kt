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

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class RestResponseExceptionHandler {
    @ExceptionHandler(
        BadCredentialsException::class,
        UsernameNotFoundException::class,
        UserAlreadyExistsException::class,
    )
    final fun handleException(exception: Exception, request: WebRequest): ResponseEntity<RestApiError> {
        val headers = HttpHeaders()
        return when (exception) {
            is UserAlreadyExistsException -> handleUserAlreadyExistsException(exception, headers, request)
            is UsernameNotFoundException -> handleUsernameNotFoundException(exception, headers, request)
            is BadCredentialsException -> handleBadCredentialsException(exception, headers, request)
            else -> handleExceptionInternal(exception, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request)
        }
    }

    protected fun handleUserAlreadyExistsException(
        exception: UserAlreadyExistsException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("User already exists")
        logger.error(exception) { error.message }
        return handleExceptionInternal(exception, error, headers, HttpStatus.BAD_REQUEST, request)
    }

    protected fun handleUsernameNotFoundException(
        exception: UsernameNotFoundException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("User not found")
        logger.error(exception) { error.message }
        return handleExceptionInternal(exception, error, headers, HttpStatus.NOT_FOUND, request)
    }

    protected fun handleBadCredentialsException(
        exception: BadCredentialsException,
        headers: HttpHeaders,
        request: WebRequest,
    ): ResponseEntity<RestApiError> {
        val error = RestApiError("Bad credentials provided")
        logger.error(exception) { error.message }
        return handleExceptionInternal(exception, error, headers, HttpStatus.UNAUTHORIZED, request)
    }

    protected fun handleExceptionInternal(
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
