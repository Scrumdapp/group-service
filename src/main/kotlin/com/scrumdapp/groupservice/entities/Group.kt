package com.scrumdapp.groupservice.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "groups")
class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false, length = 64)
    var name: String? = null

    @Column(nullable = false)
    var group_owner: Int = 0

    @Column(nullable = true)
    var background_preference: Int? = null

    @Column(nullable = false)
    var is_active: Boolean = false

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL])
    var features: MutableList<GroupFeature> = mutableListOf()

}