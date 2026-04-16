package com.scrumdapp.checkinservice.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(ex: AppException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity
            .status(ex.status)
            .body(
                mapOf(
                    "error" to ex.message!!,
                    "status" to ex.status.value()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<Map<String, Any>> {
        return ResponseEntity
            .status(500)
            .body(
                mapOf(
                    "error" to "Something went wrong",
                    "status" to 500
                )
            )
    }
}