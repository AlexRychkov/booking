package org.booking.device

import org.booking.common.DEVICE_CREATED_TOPIC
import org.booking.common.Event.DeviceCreated
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeviceEventListener(
    private val createDevice: CreateDeviceCommand,
) {

    @KafkaListener(id = "DeviceCreated", topics = [DEVICE_CREATED_TOPIC])
    fun userCreated(deviceCreate: DeviceCreated) {
        createDevice(deviceCreate).subscribe()
    }
}