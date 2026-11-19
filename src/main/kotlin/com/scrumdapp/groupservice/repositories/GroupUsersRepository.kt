package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.dto.GroupMemberCount
import com.scrumdapp.groupservice.entities.GroupUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GroupUsersRepository : JpaRepository<GroupUsers, Long> {

    fun findByGroupIdAndUserId(groupId: Long, userId: Long): Optional<GroupUsers>

    fun findByGroupId(groupId: Long): List<GroupUsers>

    fun existsByGroupIdAndUserId(groupId: Long, userId: Long): Boolean

    @Query("""
        SELECT NEW com.scrumdapp.groupservice.dto.GroupMemberCount (
            gu.group,
            COUNT(*) 
        )
        FROM GroupUsers gu
        WHERE gu.group IN (
            SELECT g.group
            FROM GroupUsers g
            WHERE g.userId = :userId
        )
        GROUP BY gu.group
    """)
    fun findGroupsByUserId(@Param("userId") userId: Long): List<GroupMemberCount>

    @Query("""
        SELECT NEW com.scrumdapp.groupservice.dto.GroupMemberCount (
            gu.group,
            COUNT(*) 
        )
        FROM GroupUsers gu
        WHERE gu.group.id = :groupId
        GROUP BY gu.group
    """)
    fun findGroupByGroupId(@Param("groupId") groupId: Long): GroupMemberCount?

    @Query("""
        SELECT NEW com.scrumdapp.groupservice.dto.GroupMemberCount (
            gu.group,
            COUNT(*) 
        )
        FROM GroupUsers gu
        WHERE gu.group.id = :groupId
        AND EXISTS (
            SELECT g.userId
            FROM GroupUsers g
            WHERE g.userId = :userId
            AND g.group.id = :groupId
        )
        GROUP BY gu.group
    """)
    fun findGroupByGroupIdAndUserId(@Param("groupId") groupId: Long, @Param("userId") userId: Long): GroupMemberCount?
}

