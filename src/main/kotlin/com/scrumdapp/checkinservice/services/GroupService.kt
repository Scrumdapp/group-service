package com.scrumdapp.checkinservice.services

import com.scrumdapp.checkinservice.dto.CreateGroupDto
import com.scrumdapp.checkinservice.dto.GroupResponseDto
import com.scrumdapp.checkinservice.dto.UpdateGroupDto
import com.scrumdapp.checkinservice.mappers.GroupMapper
import com.scrumdapp.checkinservice.repositories.GroupRepository
import com.scrumdapp.checkinservice.exceptions.NotFoundException
import com.scrumdapp.checkinservice.exceptions.ForbiddenException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository
) {

    fun getAll(page: Int, size: Int): List<GroupResponseDto> {
        val pageable = PageRequest.of(page, size)

        return groupRepository.findAll(pageable)
            .map(GroupMapper::toResponseDto)
            .content
    }

    fun getById(id: Int): GroupResponseDto {
        val group = groupRepository.findById(id)
            .orElseThrow { NotFoundException("Group with id $id not found") }

        return GroupMapper.toResponseDto(group)
    }

    fun create(dto: CreateGroupDto, role: String): GroupResponseDto {
        if (role != "docent") {
            throw ForbiddenException("Only teachers (docent) can create groups")
        }

        val group = GroupMapper.fromCreateDto(dto)
        val saved = groupRepository.save(group)

        return GroupMapper.toResponseDto(saved)
    }

    fun update(id: Int, dto: UpdateGroupDto, currentUserId: Int): GroupResponseDto {
        val existing = groupRepository.findById(id)
            .orElseThrow { NotFoundException("Group with id $id not found") }

        if (existing.group_owner != currentUserId) {
            throw ForbiddenException("You are not the owner of this group")
        }

        val updated = GroupMapper.updateFromDto(existing, dto)
        val saved = groupRepository.save(updated)

        return GroupMapper.toResponseDto(saved)
    }

    fun delete(id: Int, currentUserId: Int) {
        val existing = groupRepository.findById(id)
            .orElseThrow { NotFoundException("Group with id $id not found") }

        if (existing.group_owner != currentUserId) {
            throw ForbiddenException("You are not the owner of this group")
        }

        groupRepository.deleteById(id)
    }


}