package com.scrumdapp.groupservice.configs

import com.scrumdapp.passportplugin.filters.PassportAuthFilter
import com.scrumdapp.passportplugin.filters.usePassport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import tools.jackson.databind.ObjectMapper
import org.springframework.http.MediaType

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val passportAuthFilter: PassportAuthFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .usePassport(passportAuthFilter)
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/groups").hasAnyAuthority("STUDENT", "COACH")
                it.requestMatchers(HttpMethod.GET, "/groups/user/*").hasAuthority("GATEWAY")
                it.requestMatchers(HttpMethod.GET, "/groups/**").hasAnyAuthority("STUDENT", "COACH", "GATEWAY")
                it.requestMatchers(HttpMethod.POST, "/groups/**").hasAuthority("COACH")
                it.requestMatchers(HttpMethod.PATCH, "/groups/**").hasAuthority("COACH")
                it.requestMatchers(HttpMethod.DELETE, "/groups/**").hasAuthority("COACH")
            }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.status = 401
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(
                        response.outputStream,
                        mapOf("code" to 401, "message" to "Unauthorised")
                    )
                }
                it.accessDeniedHandler { _, response, _ ->
                    response.status = 403
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(
                        response.outputStream,
                        mapOf("code" to 403, "message" to "Access denied")
                    )
                }
            }
        return http.build()
    }
}