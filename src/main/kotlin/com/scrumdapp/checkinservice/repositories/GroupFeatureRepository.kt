package com.scrumdapp.checkinservice.repositories

import com.scrumdapp.checkinservice.entities.GroupFeature
import org.springframework.data.jpa.repository.JpaRepository

interface GroupFeatureRepository : JpaRepository<GroupFeature, Int>{
    fun findByKey(key: Int, string: String): List<GroupFeature>

}