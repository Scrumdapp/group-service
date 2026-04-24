package com.scrumdapp.checkinservice.mappers

import com.scrumdapp.checkinservice.dto.PartialUserDto
import com.scrumdapp.checkinservice.entities.GroupUsers

fun GroupUsers.toPartialUserDto() = PartialUserDto(
    userId    = user?.id,
    groupId   = group?.id,
    firstName = user?.firstName,
    lastName  = user?.lastName
)