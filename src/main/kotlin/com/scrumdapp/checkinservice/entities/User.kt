package com.scrumdapp.checkinservice.entities

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

enum class UserRoles {
    STUDENT, COACH
}

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int = 0

    var discordId: Long? = null

    var firstName: String? = null
    var lastName: String? = null

    var avatarUrl: String? = null

    @Enumerated(EnumType.STRING)
    var role: UserRoles = UserRoles.STUDENT

}