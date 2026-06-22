package com.scrumdapp.groupservice.mappers

import com.scrumdapp.groupservice.dto.CreateGroupDto
import com.scrumdapp.groupservice.dto.GroupResponseDto
import com.scrumdapp.groupservice.dto.PartialGroupResponseDto
import com.scrumdapp.groupservice.dto.PartialUserDto
import com.scrumdapp.groupservice.dto.UpdateGroupDto
import com.scrumdapp.groupservice.entities.Group
import com.scrumdapp.groupservice.entities.GroupFeature
import com.scrumdapp.groupservice.services.PartialUser

object GroupMapper {

    fun toResponseDto(group: Group, features: List<GroupFeature> = emptyList()): GroupResponseDto =
        GroupResponseDto(
            id = group.id,
            name = group.name,
            group_owner = group.group_owner,
            background_preference = group.background_preference,
            is_active = group.is_active,
            feature = features.map { it.id }
        )

    fun toPartialDto(group: Group): PartialGroupResponseDto =
        PartialGroupResponseDto(
            id = group.id,
            name = group.name,
            background_preference = group.background_preference
        )

    fun fromCreateDto(dto: CreateGroupDto, ownerId: Long): Group =
        Group().apply {
            name = dto.name
            is_active = true
            group_owner = ownerId
        }

    fun updateFromDto(group: Group, dto: UpdateGroupDto): Group =
        group.apply {
            dto.name?.let { name = it }
            dto.background_preference?.let { background_preference = it }
            dto.is_active?.let { is_active = it }
        }

    fun toGroupUserResponseDto(groupId: Long, user: PartialUser): PartialUserDto {
        val fullName = user.name.split(" ")
        return PartialUserDto(
            group_id = groupId,
            user_id = user.id,
            first_name = fullName[0],
            last_name = fullName.drop(1).joinToString()
        )
    }
}