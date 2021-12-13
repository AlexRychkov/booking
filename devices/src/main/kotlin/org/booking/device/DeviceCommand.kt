package org.booking.device

import org.booking.common.ACommandHandler
import org.booking.jwt.TokenService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreateDeviceCommand(
    private val deviceRepository: IDeviceRepository,
    private val deviceApi: DeviceApiRepository,
    private val deviceCreatedEvent: DeviceCreatedEvent,
) : ACommandHandler<CreateDevice, Mono<DeviceItem>>() {

    override fun validate(payload: CreateDevice) {
        if (payload.name.isEmpty()) {
            throw IllegalArgumentException("device name cannot be empty")
        }
    }

    override fun processInternal(payload: CreateDevice): Mono<DeviceItem> {
        return deviceApi.findByName(payload.name)
            .map { it.toDevice() }
            .flatMap { deviceRepository.save(it) }
            .map { it.toDeviceItem() }
            .doOnSuccess { deviceCreatedEvent(it.toDeviceCreatedEvent()) }
    }
}