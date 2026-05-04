package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.GroupFeature
import org.springframework.data.jpa.repository.JpaRepository

interface GroupFeatureRepository : JpaRepository<GroupFeature, Int>{
    fun findByGroupId(groupId: Int): List<GroupFeature>
}