package com.scrumdapp.checkinservice.exceptions

import org.springframework.http.HttpStatus

open class AppException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message)


class NotFoundException(message: String) :
    AppException(message, HttpStatus.NOT_FOUND)

class ForbiddenException(message: String) :
    AppException(message, HttpStatus.FORBIDDEN)

class BadRequestException(message: String) :
    AppException(message, HttpStatus.BAD_REQUEST)

class UnauthorizedException(message: String) :
    AppException(message, HttpStatus.UNAUTHORIZED)