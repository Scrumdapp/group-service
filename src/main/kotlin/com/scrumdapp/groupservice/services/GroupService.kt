package com.scrumdapp.groupservice.services

import com.scrumdapp.groupservice.dto.CreateGroupDto
import com.scrumdapp.groupservice.dto.GroupResponseDto
import com.scrumdapp.groupservice.dto.PartialUserDto
import com.scrumdapp.groupservice.dto.UpdateGroupDto
import com.scrumdapp.groupservice.entities.GroupUsers
import com.scrumdapp.groupservice.mappers.GroupMapper
import com.scrumdapp.groupservice.repositories.GroupRepository
import com.scrumdapp.groupservice.repositories.GroupUsersRepository
import com.scrumdapp.groupservice.exceptions.NotFoundException
import com.scrumdapp.groupservice.exceptions.ForbiddenException
import org.springframework.stereotype.Service
import com.scrumdapp.groupservice.repositories.GroupFeatureRepository
import com.scrumdapp.groupservice.repositories.UserRepository
import com.scrumdapp.groupservice.mappers.toPartialUserDto

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

    fun getUsersByGroupId(groupId: Int): List<PartialUserDto> =
        groupUsersRepository.findByGroupId(groupId).map { it.toPartialUserDto() }

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

    fun addUser(groupId: Int, userId: Int) {
        val group = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException("Group with id $groupId not found") }

        val user = userRepository.findUserById(userId)
            ?: throw NotFoundException("User with id $userId not found")

        val groupUser = GroupUsers().apply {
            this.user = user
            this.group = group
        }
        groupUsersRepository.save(groupUser)
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