package org.booking.booking

import java.time.LocalDateTime

data class CreateBooking(
    val userId: String,
    val deviceId: String,
)

data class MakeReturning(
    val userId: String,
    val deviceId: String,
)

data class BookingInfo(
    val available: Boolean,
    val timeAt: LocalDateTime?,
    val whoBooked: String?,
)

data class BookingItem(
    val name: String,
    val deviceId: String,
    val available: Boolean,
    val timeAt: LocalDateTime?,
    val whoBooked: String?,
)

fun Booking.toBookingItem(deviceName: String, deviceId: String) = BookingItem(
    name = deviceName,
    deviceId = deviceId,
    available = this.status == BookingStatus.FREE,
    timeAt = this.timeAt,
    whoBooked = this.userId,
)

fun Booking.toBookingInfo() = BookingInfo(
    available = this.status == BookingStatus.FREE,
    timeAt = this.timeAt,
    whoBooked = this.userId,
)