package com.scrumdapp.groupservice.services

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.scrumdapp.groupservice.exceptions.ServerException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient.builder
import org.springframework.web.client.toEntity
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper

@JsonIgnoreProperties(ignoreUnknown = true)
data class PartialUser(
    val id: Long,
    val name: String
)

@Service
class UserRequestService(
    @Value($$"${USER_SERVICE_URL}") private val baseUrl: String,
    @Value($$"${USER_FETCH_ENDPOINT}") private val fetchEndpoint: String = "/users",
    @Value($$"${spring.application.name}") private val appName: String
) {

    private val reqBuilder = builder().baseUrl(baseUrl).build()

    private val mapper = ObjectMapper()

    fun fetchUsers(jwt: Jwt, ids: List<Long>): List<PartialUser> {

        val uri = "$fetchEndpoint?partial=true&id=${ids.joinToString(",")}"

        try {
            val res = reqBuilder.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "bearer ${jwt.tokenValue}")
                .header(HttpHeaders.VIA, appName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity<String>()

            if (res.statusCode != HttpStatus.OK) {
                throw Exception("Unexpected response from user request")
            } else {
                val body = res.body ?: throw Exception("Unexpected response from user request")
                return mapper.readValue(body, object : TypeReference<List<PartialUser>>() {})
            }
        } catch (e: Exception) {
            // Far from the cleanest way of doing this, but I cannot be bothered to also rewrite the error handling at this moment
            println(e.cause)
            throw ServerException("Something went wrong")
        }
    }
}