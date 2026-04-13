package com.scrumdapp.checkinservice.entities

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class GroupUsersId(var userId: Int = 0, var groupId: Int = 0) : Serializable

@Entity
@Table(name = "GroupUsers")
class GroupUsers {

    @EmbeddedId
    var id: GroupUsersId = GroupUsersId()

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    var group: Group? = null
}