package com.scrumdapp.checkinservice.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "groups")
class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(nullable = false)
    var name: String? = null

    @Column(nullable = true)
    var background_preference: Int? = null

    @Column(nullable = false)
    var is_active: Boolean = false

    @OneToOne(mappedBy = "group", cascade = [CascadeType.ALL])
    var feature: GroupFeature? = null
}