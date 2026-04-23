package com.scrumdapp.checkinservice.controllers

import com.scrumdapp.checkinservice.dto.PassportDto
import com.scrumdapp.checkinservice.dto.UserPatchDto
import com.scrumdapp.checkinservice.dto.UserResponseDto
import com.scrumdapp.checkinservice.services.UserService
import org.springframework.data.relational.core.sql.In
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/@me")
    fun getSelf(): UserResponseDto {
        val ownId: Int = 1 //TODO("replace this with jwt")
        return userService.getById(ownId)
    }

    @PatchMapping("/@me")
    fun patchSelf(
        @RequestBody dto: UserPatchDto
    ): UserResponseDto {
        val uId: Int = 1 //TODO("replace this with jwt")
        return userService.patchUser(dto, uId)
    }

    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable userId: Int
    ): UserResponseDto {
        val ownId: Int = 1 //TODO("replace this with jwt")
        if (userId == ownId) return userService.getById(ownId)

        return userService.getByMutualId(userId, ownId)
    }

    @GetMapping("/{userId}/passport")
    fun generatePassport(
        @PathVariable userId: Int
    ): PassportDto {
        return userService.getPassport(userId)
    }


//    @PatchMapping("/{userId}")
//    fun updateUser(@PathVariable userId: Int, @RequestBody dto: UserPatchDto): UserResponseDto {
//
//    }
}