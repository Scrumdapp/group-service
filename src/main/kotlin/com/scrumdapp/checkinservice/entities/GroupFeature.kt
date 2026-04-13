package com.scrumdapp.checkinservice.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "group_features")
class GroupFeature(

    @Id
    var key: Int? = null,

    @OneToOne
    @MapsId
    @JoinColumn(name = "key")
    var group: Group? = null,

    @Column(nullable = false)
    var description: String? = null
)