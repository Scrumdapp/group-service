package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long> {

    fun findGroupById(id: Long): Group?
    
    fun findByName(name: String): Group?
}