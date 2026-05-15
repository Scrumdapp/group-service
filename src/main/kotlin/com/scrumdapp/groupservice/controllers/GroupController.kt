package com.scrumdapp.groupservice.controllers

import com.scrumdapp.groupservice.dto.AddUserDto
import com.scrumdapp.groupservice.dto.CreateGroupDto
import com.scrumdapp.groupservice.dto.GroupResponseDto
import com.scrumdapp.groupservice.dto.PartialUserDto
import com.scrumdapp.groupservice.dto.UpdateGroupDto
import com.scrumdapp.groupservice.services.GroupService
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
    fun getAll(): List<GroupResponseDto> {
        return groupService.getAll(getCurrentUserId())
    }


    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): GroupResponseDto {
        return groupService.getById(id)
    }


    @PostMapping
    fun create(
        @Valid @RequestBody dto: CreateGroupDto
    ): ResponseEntity<GroupResponseDto> {
        val created = groupService.create(dto, getCurrentUserRole(), getCurrentUserId())

        return ResponseEntity
            .created(URI.create("/groups/${created.id}"))
            .body(created)
    }


    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @Valid @RequestBody dto: UpdateGroupDto
    ): GroupResponseDto {
        return groupService.update(id, dto, getCurrentUserId())
    }
    @PostMapping("/{groupId}/users")
    fun addUser(
        @PathVariable groupId: Int,
        @RequestBody dto: AddUserDto
    ): ResponseEntity<Void> {
        groupService.addUser(groupId, dto.userId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{groupid}/users")
    fun getUsers(@PathVariable groupid: Int): List<PartialUserDto> {
        return groupService.getUsersByGroupId(groupid)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        groupService.delete(id, getCurrentUserRole(), getCurrentUserId())
        return ResponseEntity.noContent().build()
    }
}