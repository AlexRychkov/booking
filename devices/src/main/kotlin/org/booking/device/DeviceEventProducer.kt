package org.booking.device

import org.booking.common.Event
import org.booking.common.AEventProducer
import org.booking.common.DEVICE_CREATED_TOPIC
import org.springframework.stereotype.Component

@Component
class DeviceCreatedEvent : AEventProducer<Event.DeviceCreated>(DEVICE_CREATED_TOPIC)