package com.scrumdapp.groupservice.dto

data class PartialUserDto (
    val userId: Long,
    val groupId: Long,
    val firstName: String?,
    val lastName: String?
)