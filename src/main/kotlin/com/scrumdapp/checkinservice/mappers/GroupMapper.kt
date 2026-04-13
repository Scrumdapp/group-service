package com.scrumdapp.checkinservice.mappers

import com.scrumdapp.checkinservice.dto.group.GroupDto
import com.scrumdapp.checkinservice.entities.Group
object GroupMapper {

    fun toDto(group: Group): GroupDto {
        return GroupDto(
            id = group.id,
            name = group.name,
            backgroundPreference = group.background_preference,
            isActive = group.is_active,
            feature = null,
            users = emptyList()
        )
    }

    fun toEntity(dto: GroupDto): Group {
        val group = Group()
        group.name = dto.name
        group.background_preference = dto.backgroundPreference
        group.is_active = dto.isActive

        return group
    }
}