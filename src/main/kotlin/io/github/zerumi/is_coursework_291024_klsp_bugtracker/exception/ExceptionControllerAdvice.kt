package io.github.zerumi.is_coursework_291024_klsp_bugtracker.exception

import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.boot.json.JsonParseException
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.*

@RestControllerAdvice
class ControllerExceptionHandler {
    companion object {
        private const val VALIDATION_EXCEPTION = "Validation exception"
        private const val INVALID_INPUT_EXCEPTION = "Invalid input"
        private const val NOT_FOUND_EXCEPTION = "Not found"
        private const val ACCESS_DENIED_EXCEPTION = "Access denied"
        private const val UNAUTHORIZED_EXCEPTION = "Invalid auth credentials (login/password)"

        private const val SIGNATURE_JWT_EXCEPTION = "JWT signature does not match locally computed signature"
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundException(ex: EntityNotFoundException, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.NOT_FOUND.value(), Date(), NOT_FOUND_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun methodArgumentNotValidException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), Date(), VALIDATION_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(
        value = [JsonParseException::class, HttpMessageNotReadableException::class, IllegalArgumentException::class, InvalidDataAccessApiUsageException::class, MethodArgumentTypeMismatchException::class, ConstraintViolationException::class]
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun jsonParseException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.BAD_REQUEST.value(), Date(), INVALID_INPUT_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [BadCredentialsException::class, AuthenticationException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun badCredentialsException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNAUTHORIZED.value(), Date(), UNAUTHORIZED_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun accessDeniedExceptionHandler(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.FORBIDDEN.value(), Date(), ACCESS_DENIED_EXCEPTION, ex.message)
        return ResponseEntity(message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(SignatureException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun signatureAccessException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNAUTHORIZED.value(), Date(), SIGNATURE_JWT_EXCEPTION, ex.message)
        return ResponseEntity(message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MalformedJwtException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun jwtException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNAUTHORIZED.value(), Date(), SIGNATURE_JWT_EXCEPTION, ex.message)
        return ResponseEntity(message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun globalExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        val message =
            ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), Date(), request.getDescription(false), "${ex.message} / ${ex.javaClass}")
        return ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
