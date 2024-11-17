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
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.*

@ControllerAdvice
@ResponseBody
class ControllerExceptionHandler {
    companion object {
        private const val VALIDATION_EXCEPTION = "Validation exception"
        private const val INVALID_INPUT_EXCEPTION = "Invalid input"
        private const val NOT_FOUND_EXCEPTION = "Not found"
        private const val ACCESS_DENIED_EXCEPTION = "Access denied"
        private const val UNAUTHORIZED_EXCEPTION = "Invalid password or email!"

        private const val SIGNATURE_JWT_EXCEPTION = "JWT signature does not match locally computed signature"
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun resourceNotFoundException(ex: EntityNotFoundException, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.NOT_FOUND.value(), Date(), NOT_FOUND_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), Date(), VALIDATION_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(
        value = [JsonParseException::class, HttpMessageNotReadableException::class, IllegalArgumentException::class, InvalidDataAccessApiUsageException::class, MethodArgumentTypeMismatchException::class, ConstraintViolationException::class]
    )
    fun jsonParseException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.BAD_REQUEST.value(), Date(), INVALID_INPUT_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [BadCredentialsException::class])
    fun badCredentialsException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNAUTHORIZED.value(), Date(), UNAUTHORIZED_EXCEPTION, ex.message)

        return ResponseEntity(message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedExceptionHandler(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.FORBIDDEN.value(), Date(), ACCESS_DENIED_EXCEPTION, ex.message)
        return ResponseEntity(message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(SignatureException::class)
    fun signatureAccessException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNAUTHORIZED.value(), Date(), SIGNATURE_JWT_EXCEPTION, ex.message)
        return ResponseEntity(message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun jwtException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorMessage> {
        val message = ErrorMessage(HttpStatus.UNAUTHORIZED.value(), Date(), SIGNATURE_JWT_EXCEPTION, ex.message)
        return ResponseEntity(message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        val message =
            ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), Date(), request.getDescription(false), ex.message)
        return ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}