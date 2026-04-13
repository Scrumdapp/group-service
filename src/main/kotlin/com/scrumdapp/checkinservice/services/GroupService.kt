package com.scrumdapp.checkinservice.services

import com.scrumdapp.checkinservice.dto.group.GroupDto
import com.scrumdapp.checkinservice.mappers.GroupMapper
import com.scrumdapp.checkinservice.repositories.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository
) {

    fun getAll(): List<GroupDto> {
        return groupRepository.findAll()
            .map { GroupMapper.toDto(it) }
    }

    fun getById(id: Int): GroupDto {
        val group = groupRepository.findById(id)
            .orElseThrow { RuntimeException("Group not found") }

        return GroupMapper.toDto(group)
    }

    fun create(dto: GroupDto): GroupDto {
        val entity = GroupMapper.toEntity(dto)
        val saved = groupRepository.save(entity)
        return GroupMapper.toDto(saved)
    }

    fun update(id: Int, dto: GroupDto): GroupDto {
        val existing = groupRepository.findById(id)
            .orElseThrow { RuntimeException("Group not found") }

        existing.name = dto.name
        existing.background_preference = dto.backgroundPreference
        existing.is_active = dto.isActive

        return GroupMapper.toDto(groupRepository.save(existing))
    }

    fun delete(id: Int) {
        if (!groupRepository.existsById(id)) {
            throw RuntimeException("Group not found")
        }
        groupRepository.deleteById(id)
    }
}