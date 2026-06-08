package com.scrumdapp.groupservice.services

import com.scrumdapp.groupservice.exceptions.BadRequestException
import com.scrumdapp.groupservice.exceptions.ServerException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient.builder
import org.springframework.web.client.toEntity
import tools.jackson.databind.ObjectMapper
import tools.jackson.core.type.TypeReference

data class InviteValidationResponse(
    val valid: Boolean
)

@Service
class InviteRequestService(
    @Value($$"${INVITE_SERVICE_URL}") private val baseUrl: String,
    @Value($$"${INVITE_VALIDATION_URL:/invites/safety}") private val inviteValidationUrl: String,
    @Value($$"${spring.application.name}") private val appName: String,
) {

    private val reqBuilder = builder().baseUrl(baseUrl).build()

    private val mapper = ObjectMapper()



    fun validateInviteSafetyToken(jwt: Jwt, token: String): InviteValidationResponse {
        val uri = "$inviteValidationUrl?token=$token"

        try {
            val res = reqBuilder.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ${jwt.tokenValue}")
                .header(HttpHeaders.VIA, appName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity<String>()

            if (res.statusCode != HttpStatus.OK) {
                throw Exception("Unexpected response from InviteRequestService")
            } else {
                val body = mapper.readValue(res.body, InviteValidationResponse::class.java)
                return body
            }
        } catch (e: Exception) {
            println(e)
            throw BadRequestException("Couldn't reach downstream service")
        }
    }
}