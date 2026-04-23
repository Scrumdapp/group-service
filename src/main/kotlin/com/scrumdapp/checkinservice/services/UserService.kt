package com.scrumdapp.checkinservice.services

import com.scrumdapp.checkinservice.dto.UserResponseDto
import com.scrumdapp.checkinservice.repositories.GroupUsersRepository
import com.scrumdapp.checkinservice.repositories.UserRepository
import org.springframework.stereotype.Service

interface UserService {
    fun getById(id: Int): UserResponseDto
    fun getByDiscordId()

}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val groupUserRepository: GroupUsersRepository
)