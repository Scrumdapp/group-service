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
import com.scrumdapp.passportplugin.jwt.PassportContent
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupUsersRepository: GroupUsersRepository,
    private val groupFeatureRepository: GroupFeatureRepository,
    private val userRequestService: UserRequestService,
) {

    fun getAll(userId: Long): List<GroupResponseDto> {
        return groupUsersRepository.findByUser(userId)
            .map { it.group }
            .map(GroupMapper::toResponseDto)
    }

    fun getPartialUsers(groupId: Long, userId: Long): List<PartialUserDto> {
        val groupUsers = groupUsersRepository.findByGroupId(groupId)
        if (groupUsers.isEmpty() || groupUsers.find { it.user == userId } == null) throw ForbiddenException("Insufficient permission to access this group")

        val groupUser = fetchUsernames(groupUsers.map { it.user })
        return groupUser.map { GroupMapper.toPartialResponseDto(groupId, it) }
    }

    fun getById(groupId: Long, userId: Long): GroupResponseDto {

        val groupUser = groupUsersRepository.findDistinctByUserAndGroupId(userId, groupId)

        if (groupUser.isEmpty()) throw ForbiddenException("Insufficient permission to access to this group")

        val group = groupUser[0].group ?: throw NotFoundException("Group not found")

        val features = groupFeatureRepository.findByGroupId(group.id)
        return GroupMapper.toResponseDto(group, features)
    }

    fun create(dto: CreateGroupDto, role: String, userId: Long): GroupResponseDto {


        if (role != "COACH") {
            throw ForbiddenException("Only coaches (docent) can create groups")
        }

        val group = GroupMapper.fromCreateDto(dto, userId)
        val saved = groupRepository.save(group)

        val groupUser = GroupUsers().apply {
            this.user = userId
            this.group = saved
        }
        groupUsersRepository.save(groupUser)

        return GroupMapper.toResponseDto(saved)
    }

    fun update(groupId: Long, dto: UpdateGroupDto, currentUserId: Long): GroupResponseDto {
        val existing = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException("Group with id $groupId not found") }

        if (existing.group_owner != currentUserId) {
            throw ForbiddenException("You are not the owner of this group")
        }

        val updated = GroupMapper.updateFromDto(existing, dto)
        val saved = groupRepository.save(updated)

        return GroupMapper.toResponseDto(saved)
    }

    fun addUser(groupId: Long, userId: Long) {
        val group = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException("Group with id $groupId not found") }

        val groupUser = GroupUsers().apply {
            this.user = userId
            this.group = group
        }
        groupUsersRepository.save(groupUser)
    }

    fun deactivate(groupId: Long, passport: PassportContent): Boolean {
        val existing = groupRepository.findById(groupId)
            .orElseThrow { NotFoundException("Group with id $groupId not found") }

        if (passport.roles == null || passport.roles?.contains("COACH") == false) {
            throw ForbiddenException("Only teachers (docent) can delete groups")
        }

        if (existing.group_owner != passport.userId.toLong()) {
            throw ForbiddenException("You are not the owner of this group")
        }

        existing.is_active = false
        groupRepository.save(existing)

        return true
    }

    private fun fetchUsernames(ids: List<Long>): List<PartialUser> {
        val principal = SecurityContextHolder.getContext().authentication as Jwt
        return userRequestService.fetchUsers(principal, ids)
    }
}