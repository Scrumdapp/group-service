package com.scrumdapp.checkinservice.controllers

import com.scrumdapp.checkinservice.dto.group.GroupDto
import com.scrumdapp.checkinservice.services.GroupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(
    private val groupService: GroupService
) {

    @GetMapping
    fun getAll(): List<GroupDto> =
        groupService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<GroupDto> =
        ResponseEntity.ok(groupService.getById(id))

    @PostMapping
    fun create(@RequestBody dto: GroupDto): ResponseEntity<GroupDto> =
        ResponseEntity.ok(groupService.create(dto))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody dto: GroupDto
    ): ResponseEntity<GroupDto> =
        ResponseEntity.ok(groupService.update(id, dto))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        groupService.delete(id)
        return ResponseEntity.noContent().build()
    }
}