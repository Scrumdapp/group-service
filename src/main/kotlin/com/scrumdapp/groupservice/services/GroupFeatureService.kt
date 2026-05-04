package com.scrumdapp.groupservice.services
import com.scrumdapp.groupservice.dto.GroupFeatureDto
import com.scrumdapp.groupservice.exceptions.NotFoundException
import com.scrumdapp.groupservice.mappers.GroupFeatureMapper
import com.scrumdapp.groupservice.repositories.GroupFeatureRepository
import com.scrumdapp.groupservice.repositories.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupFeatureService(
    private val groupFeatureRepository: GroupFeatureRepository,
    private val groupRepository: GroupRepository
) {

    fun create(dto: GroupFeatureDto): GroupFeatureDto {
        val group = groupRepository.findById(dto.key!!)
            .orElseThrow { NotFoundException("Group with id ${dto.key} not found") }

        val entity = GroupFeatureMapper.toEntity(dto, group)
        val saved = groupFeatureRepository.save(entity)

        return GroupFeatureMapper.toDto(saved)
    }
}