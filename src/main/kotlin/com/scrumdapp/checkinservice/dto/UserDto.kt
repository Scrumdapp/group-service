package com.scrumdapp.checkinservice.dto

import jakarta.validation.constraints.Size

data class UserResponseDto(
    val id: Int,
    val discordId: Long?,

    val firstName: String? = null,
    val lastName: String? = null,

    val avatar: String? = null,

    val role: String
)

data class UserPatchDto(

    val discordId: Long? = null,

    @Size(max = 50)
    val firstName: String? = null,
    @Size(max = 100)
    val lastName: String? = null,

    val avatar: String? = null,
)

data class UserCreateDto(

    val discordId: Long,
    val firstName: String? = null,
    val lastName: String? = null,
    val avatar: String? = null,

    val role: String
)