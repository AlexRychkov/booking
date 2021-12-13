package org.booking.auth.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordUtil {
    private val bcryptEncoder = BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.`$2A`, 13)

    fun encode(password: String): String = bcryptEncoder.encode(password)

    fun matches(password: String, hash: String): Boolean = bcryptEncoder.matches(password, hash)
}