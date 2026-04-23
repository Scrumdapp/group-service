package com.scrumdapp.checkinservice.mappers

import com.scrumdapp.checkinservice.dto.UserCreateDto
import com.scrumdapp.checkinservice.dto.UserPatchDto
import com.scrumdapp.checkinservice.dto.UserResponseDto
import com.scrumdapp.checkinservice.entities.User
import com.scrumdapp.checkinservice.entities.UserRoles

fun User.toResponseDto(): UserResponseDto {
    return UserResponseDto(
        id = id,
        discordId = discordId,
        firstName = firstName,
        lastName = lastName,
        avatar = avatarUrl,
        role = role.name
    )
}

fun User.patchFromDto(dto: UserPatchDto): User = apply {
    dto.firstName?.let { firstName = it }
    dto.lastName?.let { lastName = it }
    dto.avatar?.let { avatarUrl = it }
}

fun UserCreateDto.toEntity(): User {
    return User().apply {

        discordId = this@toEntity.discordId
        firstName = this@toEntity.firstName
        lastName = this@toEntity.lastName
        avatarUrl = this@toEntity.avatar
        role = UserRoles.valueOf(this@toEntity.role.uppercase())
    }
}