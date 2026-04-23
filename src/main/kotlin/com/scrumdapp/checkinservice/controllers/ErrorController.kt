package com.scrumdapp.checkinservice.controllers

import com.scrumdapp.checkinservice.exceptions.BadRequestException
import com.scrumdapp.checkinservice.exceptions.ForbiddenException
import com.scrumdapp.checkinservice.exceptions.NotFoundException
import com.scrumdapp.checkinservice.exceptions.ServerFaultException
import com.scrumdapp.checkinservice.exceptions.UnauthorizedException
import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.webmvc.error.ErrorController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExceptionController: ErrorController {

    @GetMapping("/error")
    fun error(request: HttpServletRequest) {
        val statusCode = Integer.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString())

        println(statusCode)
        when (statusCode) {
            400 -> throw BadRequestException("Bad Request")
            401 -> throw UnauthorizedException("Not authorized")
            403 -> throw ForbiddenException("Forbidden")
            404 -> throw NotFoundException("Not found")
            500 -> throw ServerFaultException("Unexpected error")
            else -> throw ServerFaultException("Unexpected error")
        }
    }
}