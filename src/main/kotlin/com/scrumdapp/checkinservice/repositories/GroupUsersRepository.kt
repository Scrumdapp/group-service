package com.scrumdapp.checkinservice.repositories

import com.scrumdapp.checkinservice.dto.PartialUserDto
import com.scrumdapp.checkinservice.entities.GroupUsers
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUsersRepository : JpaRepository<GroupUsers, Int> {

    fun findByUser(userId: Int): List<GroupUsers>

    fun findByUserId(userId: Int): List<GroupUsers>

    fun findByGroupId(groupId: Int): List<GroupUsers>
}