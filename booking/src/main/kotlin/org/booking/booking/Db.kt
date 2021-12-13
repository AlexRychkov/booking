package org.booking.booking

import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID

typealias BookingId = Long

enum class BookingStatus {
    FREE, BOOKED
}

data class Booking(
    @Id val id: BookingId? = null,
    val publicId: String = UUID.randomUUID().toString(),
    val deviceId: String,
    val status: BookingStatus,
    val timeAt: LocalDateTime? = null,
    val userId: String? = null,
)