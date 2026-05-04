package com.scrumdapp.groupservice.repositories

import com.scrumdapp.groupservice.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {

    fun findByDiscordId(discordId: Long): User?
    fun findUserById(userId: Int): User?
}