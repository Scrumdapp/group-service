package com.scrumdapp.groupservice.dto

import jakarta.validation.constraints.Size

data class PartialUserDto (
    val userId: Long,
    val groupId: Long,
    @field:Size(min = 3, max = 64)
    val firstName: String?,
    @field:Size(min = 3, max = 64)
    val lastName: String?
)