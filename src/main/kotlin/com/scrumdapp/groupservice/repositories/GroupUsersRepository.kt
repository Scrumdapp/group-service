package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.GroupUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GroupUsersRepository : JpaRepository<GroupUsers, Long> {

    fun findDistinctByUserAndGroupId(userId: Long, groupId: Long): List<GroupUsers>
    fun findByGroupIdAndUser(groupId: Long, userId: Long): Optional<GroupUsers>

    fun findByUser(userId: Long): List<GroupUsers>

    fun findByGroupId(groupId: Long): List<GroupUsers>

    fun existsByGroupIdAndUser(groupId: Long, userId: Long): Boolean
}