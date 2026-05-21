package com.scrumdapp.groupservice.configs

import com.scrumdapp.passportplugin.filters.PassportAuthFilter
import com.scrumdapp.passportplugin.filters.usePassport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

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
                it.requestMatchers(HttpMethod.GET, "/groups/*").hasAnyAuthority("STUDENT", "COACH", "GATEWAY")
                it.requestMatchers((HttpMethod.POST), "/groups/**").hasAuthority("COACH")
                it.requestMatchers(HttpMethod.PATCH, "/groups/**").hasAuthority("COACH")
                it.requestMatchers(HttpMethod.DELETE, "/groups/**").hasAuthority("COACH")
            }
        return http.build()
    }
}