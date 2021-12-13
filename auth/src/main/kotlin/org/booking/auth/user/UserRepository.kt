package org.booking.auth.user

import org.booking.common.Event.UserRole
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface IUserRepository : ReactiveCrudRepository<User, UserId> {

    fun findByEmail(email: Email): Mono<User>

    @Modifying
    @Query("update users set enabled = false where public_id = :publicId")
    fun setDisabled(publicId: String): Mono<Void>

    @Modifying
    @Query("update users set role = :role where public_id = :publicId")
    fun setRole(publicId: String, role: UserRole): Mono<Void>
}