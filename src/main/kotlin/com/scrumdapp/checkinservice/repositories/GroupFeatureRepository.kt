package com.scrumdapp.checkinservice.repositories

import com.scrumdapp.checkinservice.entities.GroupFeature
import org.springframework.data.jpa.repository.JpaRepository

interface GroupFeatureRepository : JpaRepository<GroupFeature, Int>{
    fun findByKey(key: String): GroupFeature?
    fun key(key: Int): MutableList<GroupFeature>


}