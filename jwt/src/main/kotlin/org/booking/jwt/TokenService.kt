package org.booking.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.*

interface ITokenService {
    fun generate(userId: String, role: String): JwtToken
}

@Service
class TokenService : ITokenService {
    private val secret: String = "secret"
    private val oneMinuteMs = 60000L
    private val tokenValidityMs = oneMinuteMs * 60 * 24 * 5

    companion object {
        const val AUTHORITIES_KEY = "role"
    }

    private val algorithm = Algorithm.HMAC512(secret)
    private val verifierWithExpiration = JWT.require(algorithm).build()

    private fun validity() = Date(System.currentTimeMillis() + tokenValidityMs)

    override fun generate(userId: String, role: String): JwtToken = JWT.create()
        .withSubject(userId.toString())
        .withExpiresAt(validity())
        .withClaim(AUTHORITIES_KEY, role)
        .sign(algorithm)

    fun authentication(token: String): Authentication {
        val claims = claims(token)!!
        return claims.auth()
    }

    fun status(token: String): JwtStatus = try {
        verifierWithExpiration.verify(token)
        JwtStatus.OK
    } catch (e: TokenExpiredException) {
        JwtStatus.EXPIRED
    } catch (e: JWTVerificationException) {
        JwtStatus.INVALID
    } catch (e: IllegalArgumentException) {
        JwtStatus.INVALID
    }

    fun claims(token: String): JwtPayload? = try {
        val jwt = JWT.decode(token)
        val role = jwt.claims[AUTHORITIES_KEY]!!.asString()
        JwtPayload(
            jwt.expiresAt,
            jwt.subject,
            SimpleGrantedAuthority(role)
        )
    } catch (exception: JWTDecodeException) {
        null
    }
}

fun main(args: Array<String>) {
    println(TokenService().generate("userId", "ADMIN"))
}