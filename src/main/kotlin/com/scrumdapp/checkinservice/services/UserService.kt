package com.scrumdapp.checkinservice.services

import com.scrumdapp.checkinservice.dto.UserCreateDto
import com.scrumdapp.checkinservice.dto.UserPatchDto
import com.scrumdapp.checkinservice.dto.UserResponseDto
import com.scrumdapp.checkinservice.exceptions.ForbiddenException
import com.scrumdapp.checkinservice.exceptions.NotFoundException
import com.scrumdapp.checkinservice.mappers.patchFromDto
import com.scrumdapp.checkinservice.mappers.toEntity
import com.scrumdapp.checkinservice.mappers.toResponseDto
import com.scrumdapp.checkinservice.repositories.GroupUsersRepository
import com.scrumdapp.checkinservice.repositories.UserRepository
import org.springframework.stereotype.Service

interface UserService {
    fun getById(id: Int): UserResponseDto
    fun getByMutualId(id: Int, ownId: Int): UserResponseDto
    fun getByDiscordId(id: Long): UserResponseDto

    fun createUser(dto: UserCreateDto): UserResponseDto
    fun patchUser(dto: UserPatchDto, userId: Int): UserResponseDto
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val groupUserRepository: GroupUsersRepository
): UserService {
    override fun getById(id: Int): UserResponseDto {
        val user = userRepository.findUserById(id) ?: throw NotFoundException("User with id $id not found")
        return user.toResponseDto()
    }

    override fun getByMutualId(id: Int, ownId: Int): UserResponseDto {
        val mutualGroups = groupUserRepository.findMutualGroups(id, ownId)
        if (mutualGroups.isEmpty()) throw ForbiddenException("Forbidden")

        val user = userRepository.findUserById(id) ?: throw NotFoundException("User with id $id not found")
        return user.toResponseDto()
    }

    override fun getByDiscordId(id: Long): UserResponseDto {
        val user = userRepository.findByDiscordId(id) ?: throw NotFoundException("User with discordId $id not found")
        return user.toResponseDto()
    }

    override fun createUser(dto: UserCreateDto): UserResponseDto {
        return userRepository.save(dto.toEntity()).toResponseDto()
    }

    override fun patchUser(dto: UserPatchDto, userId: Int): UserResponseDto {
        val user = userRepository.findUserById(userId) ?: throw NotFoundException("User with id $userId not found")
        user.patchFromDto(dto)
        return userRepository.save(user).toResponseDto()
    }

}