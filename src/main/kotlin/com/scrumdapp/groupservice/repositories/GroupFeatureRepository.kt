package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.GroupFeature
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupFeatureRepository : JpaRepository<GroupFeature, Long>{
    fun findByGroupId(groupId: Long): List<GroupFeature>
}