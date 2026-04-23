package com.scrumdapp.checkinservice.services

import com.scrumdapp.checkinservice.dto.CreateGroupDto
import com.scrumdapp.checkinservice.dto.GroupResponseDto
import com.scrumdapp.checkinservice.dto.UpdateGroupDto
import com.scrumdapp.checkinservice.entities.GroupUsers
import com.scrumdapp.checkinservice.mappers.GroupMapper
import com.scrumdapp.checkinservice.repositories.GroupRepository
import com.scrumdapp.checkinservice.repositories.GroupUsersRepository
import com.scrumdapp.checkinservice.exceptions.NotFoundException
import com.scrumdapp.checkinservice.exceptions.ForbiddenException
import org.springframework.stereotype.Service
import com.scrumdapp.checkinservice.repositories.GroupFeatureRepository
import com.scrumdapp.checkinservice.repositories.UserRepository

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupUsersRepository: GroupUsersRepository,
    private val groupFeatureRepository: GroupFeatureRepository,
    private val userRepository: UserRepository,
) {

    fun getAll(userId: Int): List<GroupResponseDto> {
        return groupUsersRepository.findByUser(userId)
            .mapNotNull { it.group }
            .map(GroupMapper::toResponseDto)
    }

    fun getById(id: Int): GroupResponseDto {
        val group = groupRepository.findById(id)
            .orElseThrow { NotFoundException("Group with id $id not found") }
        val features = groupFeatureRepository.findByGroupId(id)
        return GroupMapper.toResponseDto(group, features)
    }

    fun create(dto: CreateGroupDto, role: String, userId: Int): GroupResponseDto {

        var user = userRepository.findUserById(userId) ?: throw NotFoundException("User with id $userId not found")

        if (role != "docent") {
            throw ForbiddenException("Only teachers (docent) can create groups")
        }

        val group = GroupMapper.fromCreateDto(dto, user.id)
        val saved = groupRepository.save(group)

        val groupUser = GroupUsers().apply {
            this.user = user
            this.group = saved
        }
        groupUsersRepository.save(groupUser)

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

    fun delete(id: Int, role: String, currentUserId: Int) {
        val existing = groupRepository.findById(id)
            .orElseThrow { NotFoundException("Group with id $id not found") }

        if (role != "docent") {
            throw ForbiddenException("Only teachers (docent) can delete groups")
        }

        if (existing.group_owner != currentUserId) {
            throw ForbiddenException("You are not the owner of this group")
        }

        existing.is_active = false
        groupRepository.save(existing)
    }
}