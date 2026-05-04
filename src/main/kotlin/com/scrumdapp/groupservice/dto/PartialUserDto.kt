package com.scrumdapp.groupservice.dto

data class PartialUserDto (
    val userId: Int?,
    val groupId: Int?,
    val firstName: String?,
    val lastName: String?
)