package com.scrumdapp.groupservice.exceptions

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class SecurityExceptionHandler(
    private val objectMapper: ObjectMapper
) {
    fun write(response: HttpServletResponse, code: Int, message: String) {
        response.status = code
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        objectMapper.writeValue(
            response.outputStream,
            ApiResponse(code = code, message = message)
        )
    }
}