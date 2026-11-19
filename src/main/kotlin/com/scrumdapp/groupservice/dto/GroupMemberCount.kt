package com.scrumdapp.groupservice.dto

import com.scrumdapp.groupservice.entities.Group

data class GroupMemberCount(
    val group: Group,
    val userCount: Long
)