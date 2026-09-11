package com.scrumdapp.groupservice.dto
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateGroupDto(
    @field:Size(min = 3, message = "group name must be at least 3 characters", max = 64)
    @field:Pattern(
        regexp = "^[a-zA-Z0-9-_#!?(),. ]*$",
        message = "Only the characters a-z, A-Z, 0-9, -_#!>(),. are allowed"
    )
        val name: String,
    val backgroundPreference: Int?
)