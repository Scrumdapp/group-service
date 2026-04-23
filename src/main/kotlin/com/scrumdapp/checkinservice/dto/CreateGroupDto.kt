package com.scrumdapp.checkinservice.dto
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateGroupDto(
    @field:Size(min = 3, message = "group name must be at least 3 characters")
    val name: String,
    @Pattern(
        regexp = "^[a-zA-Z0-9#!?]+([ ][a-zA-Z0-9#!?]+)*$",
        message = "Only a-z, A-Z, 1-9, # ! ? allowed"
    )
    val backgroundPreference: Int?
)