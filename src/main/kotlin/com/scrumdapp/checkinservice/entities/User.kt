package com.scrumdapp.checkinservice.entities

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

enum class UserRoles {
    STUDENT, DOCENT
}

@Converter(autoApply = false)
class UserRolesConverter : AttributeConverter<UserRoles, String> {
    override fun convertToDatabaseColumn(attribute: UserRoles?): String? =
        attribute?.name

    override fun convertToEntityAttribute(dbData: String?): UserRoles? =
        dbData?.let { UserRoles.valueOf(it.uppercase()) }
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

    @Convert(converter = UserRolesConverter::class)
    var role: UserRoles = UserRoles.STUDENT
}