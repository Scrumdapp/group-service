package com.scrumdapp.checkinservice.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "group_users")
class GroupUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    lateinit var group: Group
}