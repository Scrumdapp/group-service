package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.GroupUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GroupUsersRepository : JpaRepository<GroupUsers, Long> {

    fun findDistinctByUserIdAndGroupId(userId: Long, groupId: Long): List<GroupUsers>
    fun findByGroupIdAndUserId(groupId: Long, userId: Long): Optional<GroupUsers>

    fun findByUserId(userId: Long): List<GroupUsers>

    fun findByGroupId(groupId: Long): List<GroupUsers>

    fun existsByGroupIdAndUserId(groupId: Long, userId: Long): Boolean
}