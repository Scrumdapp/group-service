package com.scrumdapp.checkinservice.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class UpdateGroupDto(
    @Min(3)
    val name: String?,
    @Pattern(
        regexp = "^[a-zA-Z0-9#!?]+([ ][a-zA-Z0-9#!?]+)*$",
        message = "Only a-z, A-Z, 1-9, # ! ? allowed"
    )
    val backgroundPreference: Int?,
    val isActive: Boolean?
)