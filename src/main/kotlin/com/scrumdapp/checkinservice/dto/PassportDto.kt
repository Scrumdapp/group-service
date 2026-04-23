package com.scrumdapp.checkinservice.dto

import com.scrumdapp.checkinservice.entities.UserRoles

data class PassportDto(
    val userId: Int,
    val groups: List<Int>,
    val role: UserRoles
)