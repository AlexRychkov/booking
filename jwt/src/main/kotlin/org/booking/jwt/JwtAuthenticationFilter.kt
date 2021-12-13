package org.booking.jwt

import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenService,
) : WebFilter {
    private val tokenSkipStatuses = listOf(JwtStatus.EXPIRED, JwtStatus.INVALID)

    override fun filter(exchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {
        val token = exchange.authToken() ?: return webFilterChain.filter(exchange)

        val jwtStatus = tokenService.status(token)
        if (jwtStatus in tokenSkipStatuses) {
            return webFilterChain.filter(exchange)
        }

        val auth = tokenService.authentication(token)
        return webFilterChain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
    }
}

const val AUTHORIZATION_HEADER = "Authorization"

fun ServerWebExchange.authToken(): String? = this.request.headers[AUTHORIZATION_HEADER]?.firstOrNull()
