package com.scrumdapp.groupservice.dto
import jakarta.validation.constraints.Size

data class CreateGroupDto(
    @field:Size(min = 3, message = "group name must be at least 3 characters", max = 64)
    val name: String,
)