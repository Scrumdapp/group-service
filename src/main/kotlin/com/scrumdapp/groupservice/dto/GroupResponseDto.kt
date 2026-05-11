package com.scrumdapp.groupservice.dto

import jakarta.validation.constraints.Size


data class GroupResponseDto(
    val id: Long,
    @field:Size(min = 3, max = 64)
    val name: String?,
    val backgroundPreference: Int?,
    val isActive: Boolean,
    val feature: GroupFeatureDto?,
    val users: List<GroupUserDto>,
    val groupOwner: Int
)