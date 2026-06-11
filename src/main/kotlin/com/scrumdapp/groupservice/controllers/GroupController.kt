package com.scrumdapp.groupservice.controllers

import com.scrumdapp.groupservice.dto.CreateGroupDto
import com.scrumdapp.groupservice.dto.GroupResponseDto
import com.scrumdapp.groupservice.dto.PartialGroupResponseDto
import com.scrumdapp.groupservice.dto.PartialUserDto
import com.scrumdapp.groupservice.dto.UpdateGroupDto
import com.scrumdapp.groupservice.exceptions.BadRequestException
import com.scrumdapp.groupservice.services.GroupService
import com.scrumdapp.passportplugin.annotations.Passport
import com.scrumdapp.passportplugin.jwt.PassportContent
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
class GroupController(
    private val groupService: GroupService
) {
    @GetMapping
    fun getAll(
        @Passport passport: PassportContent
    ): List<PartialGroupResponseDto> {
        return groupService.getAllPartial(passport.userId.toLong())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
        @Passport passport: PassportContent
    ): GroupResponseDto {
        return groupService.getById(id, passport.userId.toLong())
    }

    // Generally only used for the creation of passports
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
    ): GroupResponseDto {
        val created = groupService.create(dto, passport.userId.toLong())
        return created
    }

    @PatchMapping("/{groupId}")
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
        @RequestParam(required = false) token: String?,
        @Passport passport: PassportContent
    ): GroupResponseDto {
        if (token == null) throw BadRequestException("Verification token must be provided")

        return groupService.addUser(groupId, passport.userId.toLong(), token)
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