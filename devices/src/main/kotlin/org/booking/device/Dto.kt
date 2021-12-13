package org.booking.device

import org.booking.common.deviceCreated

data class CreateDevice(
    val name: String,
)

data class DeviceItem(
    val publicId: String,
    val name: String,
    val technology: String,
    val secondGen: String,
    val thirdGen: String,
    val fourthGen: String,
)

fun DeviceItem.toDeviceCreatedEvent() = this.let {
    deviceCreated {
        publicId = it.publicId
        name = it.name
    }
}