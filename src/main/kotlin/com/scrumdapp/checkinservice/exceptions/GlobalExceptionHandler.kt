package com.scrumdapp.checkinservice.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<ApiResponse> {
        return ResponseEntity
            .status(ex.status)
            .body(ApiResponse(
                code = ex.status.value(),
                message = ex.message ?: "Unknown error"
            ))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ApiResponse> {
        return ResponseEntity
            .status(500)
            .body(ApiResponse(
                code = 500,
                message = "Something went wrong"
            ))
    }
}