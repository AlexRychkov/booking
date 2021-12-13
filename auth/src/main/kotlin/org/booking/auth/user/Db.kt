package org.booking.auth.user

import org.booking.common.Event.UserRole
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

typealias UserId = Long

@Table("users")
data class User(
    @Id
    val id: String? = null,
    val publicId: String = UUID.randomUUID().toString(),
    val email: Email,
    val name: String,
    val password: String,
    val role: UserRole = UserRole.DEVELOPER,
    val enabled: Boolean = true
)