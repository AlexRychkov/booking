package org.booking.auth.user

import org.booking.common.Event.UserRole

typealias Email = String

data class RegisterUser(
    val name: String,
    val email: Email,
    val password: String,
)

data class LoginUser(
    val email: Email,
    val password: String,
)

data class ChangeUserRole(val publicId: String, val role: UserRole)

data class DisableUser(
        val publicId: String,
)