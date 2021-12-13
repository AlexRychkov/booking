package org.booking.device

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class DeviceListQuery(
    private val deviceRepository: IDeviceRepository,
) {

    operator fun invoke(page: Int, size: Int): Flux<DeviceItem> =
        deviceRepository.findAll(page, size).map { it.toDeviceItem() }
}