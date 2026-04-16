package com.scrumdapp.checkinservice.dto


data class GroupResponseDto(
    val id: Int,
    val name: String?,
    val backgroundPreference: Int?,
    val isActive: Boolean,
    val feature: GroupFeatureDto?,
    val users: List<GroupUserDto>,
    val groupOwner: Int
)