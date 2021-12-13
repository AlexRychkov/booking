package org.booking.device

import org.springframework.data.annotation.Id
import java.util.*

typealias DeviceId = Long

data class Device(
    @Id val id: DeviceId? = null,
    val publicId: String = UUID.randomUUID().toString(),
    val name: String,
    val technology: String,
    val secondGen: String,
    val thirdGen: String,
    val fourthGen: String,
)

fun Device.toDeviceItem() = DeviceItem(
    publicId = this.publicId,
    name = this.name,
    technology = this.technology,
    secondGen = this.secondGen,
    thirdGen = this.thirdGen,
    fourthGen = this.fourthGen,
)