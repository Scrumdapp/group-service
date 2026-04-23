package com.scrumdapp.checkinservice.services
import com.scrumdapp.checkinservice.dto.GroupFeatureDto
import com.scrumdapp.checkinservice.exceptions.NotFoundException
import com.scrumdapp.checkinservice.mappers.GroupFeatureMapper
import com.scrumdapp.checkinservice.repositories.GroupFeatureRepository
import com.scrumdapp.checkinservice.repositories.GroupRepository
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