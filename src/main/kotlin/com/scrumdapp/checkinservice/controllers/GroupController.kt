package com.scrumdapp.checkinservice.controllers

import com.scrumdapp.checkinservice.dto.CreateGroupDto
import com.scrumdapp.checkinservice.dto.GroupResponseDto
import com.scrumdapp.checkinservice.dto.UpdateGroupDto
import com.scrumdapp.checkinservice.services.GroupService
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


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        groupService.delete(id, getCurrentUserRole(), getCurrentUserId())
        return ResponseEntity.noContent().build()
    }
}