package com.scrumdapp.checkinservice.mappers

import com.scrumdapp.checkinservice.dto.CreateGroupDto
import com.scrumdapp.checkinservice.dto.GroupFeatureDto
import com.scrumdapp.checkinservice.dto.GroupResponseDto
import com.scrumdapp.checkinservice.dto.UpdateGroupDto
import com.scrumdapp.checkinservice.entities.Group
object GroupMapper {

    fun toResponseDto(group: Group): GroupResponseDto =
        GroupResponseDto(
            id = group.id,
            name = group.name,
            groupOwner = group.group_owner,
            backgroundPreference = group.background_preference,
            isActive = group.is_active,
            feature = group.feature as GroupFeatureDto?,
            users = emptyList()
        )

    fun fromCreateDto(dto: CreateGroupDto): Group =
        Group().apply {
            name = dto.name
            background_preference = dto.backgroundPreference
            is_active = true
        }

    fun updateFromDto(group: Group, dto: UpdateGroupDto): Group =
        group.apply {
            dto.name?.let { name = it }
            dto.backgroundPreference?.let { background_preference = it }
            dto.isActive?.let { is_active = it }
        }
}