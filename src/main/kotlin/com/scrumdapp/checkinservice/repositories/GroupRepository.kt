package com.scrumdapp.checkinservice.repositories

import com.scrumdapp.checkinservice.entities.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Int> {

    fun findByName(name: String): Group?

}