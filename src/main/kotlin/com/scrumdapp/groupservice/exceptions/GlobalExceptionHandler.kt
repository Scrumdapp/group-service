package com.scrumdapp.groupservice.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.MethodArgumentNotValidException

@RestControllerAdvice
@Component
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleApiException(e: AppException): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(e.status).body(ApiResponse(e.status.value(),e.message ?: "unknown error" ))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse> {
        println(e)
        return ResponseEntity.status(500).body(ApiResponse(500, "Something went wrong"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse> {
        val message = ex.bindingResult.fieldErrors
            .firstOrNull()?.defaultMessage ?: "Validation failed"

        return ResponseEntity
            .status(400)
            .body(ApiResponse(code = 400, message = message))
    }
}