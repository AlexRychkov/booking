package org.booking.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import java.util.*

typealias JwtToken = String

enum class JwtStatus {
    OK, EXPIRED, INVALID
}

data class JwtPayload(val exp: Date, val userId: String, val role: GrantedAuthority)

fun JwtPayload.auth() = UsernamePasswordAuthenticationToken(this.userId, "", listOf(this.role))
