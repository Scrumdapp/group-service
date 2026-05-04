package com.scrumdapp.groupservice.mappers

import com.scrumdapp.groupservice.dto.GroupFeatureDto
import com.scrumdapp.groupservice.entities.Group
import com.scrumdapp.groupservice.entities.GroupFeature

object GroupFeatureMapper {

    fun toDto(entity: GroupFeature): GroupFeatureDto {
        return GroupFeatureDto(
            key = entity.id,
            description = entity.description,
        )
    }

    fun toEntity(dto: GroupFeatureDto, group: Group): GroupFeature {
        val entity = GroupFeature()
        entity.group = group
        entity.description = dto.description
        return entity
    }
}