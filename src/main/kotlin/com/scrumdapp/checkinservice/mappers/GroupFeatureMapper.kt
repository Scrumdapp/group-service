package com.scrumdapp.checkinservice.mappers

import com.scrumdapp.checkinservice.dto.GroupFeatureDto
import com.scrumdapp.checkinservice.entities.Group
import com.scrumdapp.checkinservice.entities.GroupFeature

object GroupFeatureMapper {

    fun toDto(entity: GroupFeature): GroupFeatureDto {
        return GroupFeatureDto(
            key = entity.key,
            description = entity.description,
        )
    }

    fun toEntity(dto: GroupFeatureDto, group: Group): GroupFeature {
        val entity = GroupFeature()
        entity.key = dto.key
        entity.group = group
        return entity
    }
}