package org.booking.auth.http

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.WebFluxConfigurer

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
open class SecurityConfig : WebFluxConfigurer {

    @Bean
    open fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http.csrf().disable().build()

    @Bean
    open fun grantedAuthorityDefaults(): GrantedAuthorityDefaults? {
        return GrantedAuthorityDefaults("")
    }
}
