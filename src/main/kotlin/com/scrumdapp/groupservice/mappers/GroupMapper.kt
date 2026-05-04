package com.scrumdapp.groupservice.mappers

import com.scrumdapp.groupservice.dto.CreateGroupDto
import com.scrumdapp.groupservice.dto.GroupResponseDto
import com.scrumdapp.groupservice.dto.UpdateGroupDto
import com.scrumdapp.groupservice.entities.Group
import com.scrumdapp.groupservice.entities.GroupFeature

object GroupMapper {

    fun toResponseDto(group: Group, features: List<GroupFeature> = emptyList()): GroupResponseDto =
        GroupResponseDto(
            id = group.id,
            name = group.name,
            groupOwner = group.group_owner,
            backgroundPreference = group.background_preference,
            isActive = group.is_active,
            feature = features.firstOrNull()?.let { GroupFeatureMapper.toDto(it) },
            users = emptyList()
        )

    fun fromCreateDto(dto: CreateGroupDto, ownerId: Int): Group =
        Group().apply {
            name = dto.name
            background_preference = dto.backgroundPreference
            is_active = true
            group_owner = ownerId
        }

    fun updateFromDto(group: Group, dto: UpdateGroupDto): Group =
        group.apply {
            dto.name?.let { name = it }
            dto.backgroundPreference?.let { background_preference = it }
            dto.isActive?.let { is_active = it }
        }
}