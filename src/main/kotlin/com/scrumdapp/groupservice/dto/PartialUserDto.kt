package com.scrumdapp.groupservice.dto

data class PartialUserDto (
    val user_id: Long,
    val group_id: Long,
    val first_name: String,
    val last_name: String,
)