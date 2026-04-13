package com.scrumdapp.checkinservice.repositories

import com.scrumdapp.checkinservice.entities.GroupUsers
import com.scrumdapp.checkinservice.entities.GroupUsersId
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUsersRepository : JpaRepository<GroupUsers, GroupUsersId> {

    fun findByIdGroupId(groupId: Int): List<GroupUsers>

    fun findByIdUserId(userId: Int): List<GroupUsers>

}