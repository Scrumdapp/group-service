package com.scrumdapp.groupservice.controllers

import com.scrumdapp.groupservice.dto.AddUserDto
import com.scrumdapp.groupservice.dto.CreateGroupDto
import com.scrumdapp.groupservice.dto.GroupResponseDto
import com.scrumdapp.groupservice.dto.PartialUserDto
import com.scrumdapp.groupservice.dto.UpdateGroupDto
import com.scrumdapp.groupservice.services.GroupService
import com.scrumdapp.passportplugin.annotations.Passport
import com.scrumdapp.passportplugin.jwt.PassportContent
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/groups")
class GroupController(
    private val groupService: GroupService
) {

    private fun getCurrentUserId(): Int = 1

    private fun getCurrentUserRole(): String = "docent"


    @GetMapping
    fun getAll(
        @Passport passport: PassportContent
    ): List<GroupResponseDto> {
        println(passport.userId)
        return groupService.getAll(passport.userId.toLong())
    }


    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
        @Passport passport: PassportContent
    ): GroupResponseDto {
        return groupService.getById(id, passport.userId.toLong())
    }

    @GetMapping("/user/{userId}")
    fun getByUserId(
        @PathVariable userId: Long,

    ): List<GroupResponseDto> {
        return groupService.getAll(userId)
    }


    @PostMapping
    fun create(
        @Valid @RequestBody dto: CreateGroupDto,
        @Passport passport: PassportContent
    ): ResponseEntity<GroupResponseDto> {
        val created = groupService.create(dto, getCurrentUserRole(), passport.userId.toLong())

        return ResponseEntity
            .created(URI.create("/groups/${created.id}"))
            .body(created)
    }

    @PutMapping("/{groupId}")
    fun update(
        @PathVariable groupId: Long,
        @Valid @RequestBody dto: UpdateGroupDto,
        @Passport passport: PassportContent
    ): GroupResponseDto {
        return groupService.update(groupId, dto, passport.userId.toLong())
    }
    @PostMapping("/{groupId}/users")
    fun addUser(
        @PathVariable groupId: Long,
        @RequestBody dto: AddUserDto
    ): ResponseEntity<Void> {
        groupService.addUser(groupId, dto.userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{groupId}/users")
    fun getUsers(
        @PathVariable groupId: Long,
        @Passport passport: PassportContent
    ): List<PartialUserDto> {
        return groupService.getPartialUsers(groupId, passport.userId.toLong())
    }

    @DeleteMapping("/{groupId}")
    fun delete(
        @PathVariable groupId: Long,
        @Passport passport: PassportContent
    ): ResponseEntity<Void> {
        groupService.deactivate(groupId, passport)
        return ResponseEntity.noContent().build()
    }
}