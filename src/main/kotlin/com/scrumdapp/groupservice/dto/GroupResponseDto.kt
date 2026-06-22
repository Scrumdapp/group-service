package com.scrumdapp.groupservice.dto

data class PartialGroupResponseDto(
    val id: Long,
    val name: String,
    val background_preference: String?,
)

data class GroupResponseDto(
    val id: Long,
    val name: String?,
    val background_preference: String?,
    val is_active: Boolean,
    val feature: List<Long>,
    val group_owner: Long
)