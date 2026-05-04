package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Int> {

    fun findByName(name: String): Group?
}