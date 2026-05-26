package com.scrumdapp.groupservice.dto
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateGroupDto(
    @field:Size(min = 3, message = "group name must be at least 3 characters", max = 64)
    @field:Pattern(
        regexp = "^[a-zA-Z0-9#!?]+([ ][a-zA-Z0-9#!?]+)*$",
        message = "Only a-z, A-Z, 0-9, # ! ? allowed"
    )
        val name: String,
    val backgroundPreference: Int?
)