package com.scrumdapp.checkinservice.controllers

import com.scrumdapp.checkinservice.dto.UserPatchDto
import com.scrumdapp.checkinservice.dto.UserResponseDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/users")
class UserController {

    @GetMapping("/@me")
    fun getOwnUser(): UserResponseDto {

    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Int): UserResponseDto {

    }

    @PatchMapping("/{userId}")
    fun updateUser(@PathVariable userId: Int, @RequestBody dto: UserPatchDto): UserResponseDto {

    }
}