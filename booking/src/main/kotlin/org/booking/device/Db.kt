package org.booking.device

import org.booking.common.Event.DeviceCreated
import org.springframework.data.annotation.Id
import java.util.UUID

typealias DeviceId = Long

data class Device(
    @Id val id: DeviceId? = null,
    val publicId: String = UUID.randomUUID().toString(),
    val name: String,
)

fun DeviceCreated.toDevice() = Device(
    publicId = this.publicId,
    name = this.name,
)