package com.scrumdapp.checkinservice.controllers

import com.scrumdapp.checkinservice.dto.group.GroupDto
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

    @GetMapping
    fun getAll(): List<GroupDto> =
        groupService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): GroupDto =
        groupService.getById(id)

    @PostMapping
    fun create(@Valid @RequestBody dto: GroupDto): ResponseEntity<GroupDto> {
        val created = groupService.create(dto)
        return ResponseEntity
            .created(URI.create("/groups/${created.id}"))
            .body(created)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody dto: GroupDto
    ): GroupDto =
        groupService.update(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        groupService.delete(id)
    }
}