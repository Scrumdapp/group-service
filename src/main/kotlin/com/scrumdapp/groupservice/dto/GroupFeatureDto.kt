package com.scrumdapp.groupservice.dto

import jakarta.validation.constraints.Size

data class GroupFeatureDto(
    val key: Long,
    @field:Size(min = 3, max = 64)
    val description: String?
)