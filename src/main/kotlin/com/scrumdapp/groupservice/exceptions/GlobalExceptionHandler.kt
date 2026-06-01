package com.scrumdapp.groupservice.exceptions

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.MethodArgumentNotValidException

@RestControllerAdvice
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<ApiResponse> {
        return ResponseEntity
            .status(ex.status)
            .body(
                ApiResponse(
                    code = ex.status.value(),
                    message = ex.message ?: "Unauthorised"
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ApiResponse> {
        return ResponseEntity
            .status(500)
            .body(
                ApiResponse(
                    code = 500,
                    message = "Something went wrong"
                )
            )
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