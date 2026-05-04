package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.GroupUsers
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUsersRepository : JpaRepository<GroupUsers, Int> {

    fun findByUser(userId: Int): List<GroupUsers>

    fun findByUserId(userId: Int): List<GroupUsers>

    fun findByGroupId(groupId: Int): List<GroupUsers>
}