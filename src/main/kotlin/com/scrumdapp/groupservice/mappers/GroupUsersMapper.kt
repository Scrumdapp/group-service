package com.scrumdapp.groupservice.mappers

import com.scrumdapp.groupservice.dto.PartialUserDto
import com.scrumdapp.groupservice.entities.GroupUsers

fun GroupUsers.toPartialUserDto() = PartialUserDto(
    userId = user?.id,
    groupId = group?.id,
    firstName = user?.firstName,
    lastName = user?.lastName
)