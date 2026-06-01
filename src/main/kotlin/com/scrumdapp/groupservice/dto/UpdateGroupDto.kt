package com.scrumdapp.groupservice.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateGroupDto(
    @field:Size(min = 3, max = 64)
    @field:Pattern(
        regexp = "^[a-zA-Z0-9#!?]+([ ][a-zA-Z0-9#!?]+)*$",
        message = "Only a-z, A-Z, 0-9, # ! ? allowed"
    )
    val name: String?,
    val background_preference: Int?,
    val is_active: Boolean?
)