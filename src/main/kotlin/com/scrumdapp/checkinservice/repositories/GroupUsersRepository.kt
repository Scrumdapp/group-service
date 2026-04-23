package com.scrumdapp.checkinservice.repositories

import com.scrumdapp.checkinservice.entities.Group
import com.scrumdapp.checkinservice.entities.GroupUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GroupUsersRepository : JpaRepository<GroupUsers, Int> {

    fun findByUser(userId: Int): List<GroupUsers>

    fun findByGroupId(groupId: Int): List<GroupUsers>

    @Query("""
        SELECT gu1.group
        FROM GroupUsers gu1
        JOIN GroupUsers gu2 ON gu1.group.id = gu2.group.id
        WHERE gu1.user.id = :id1
        AND gu2.user.id = :id2
    """
    )
    fun findMutualGroups(
        @Param("id1") userId1: Int,
        @Param("id2") userId2: Int,
    ): List<Group>

}