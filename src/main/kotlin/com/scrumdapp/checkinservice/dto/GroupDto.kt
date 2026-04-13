package com.scrumdapp.checkinservice.dto.group

import com.scrumdapp.checkinservice.dto.GroupFeatureDto
import com.scrumdapp.checkinservice.dto.GroupUserDto

data class GroupDto(
    val id: Int,
    val name: String?,
    val backgroundPreference: Int?,
    val isActive: Boolean,
    val feature: GroupFeatureDto?,
    val users: List<GroupUserDto> = emptyList()
)