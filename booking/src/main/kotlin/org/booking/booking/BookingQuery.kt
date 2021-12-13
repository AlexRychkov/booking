package org.booking.booking

import org.booking.device.IDeviceRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.booking.common.extensions.Types.component1
import org.booking.common.extensions.Types.component2

@Component
class BookingQuery(
    private val bookingRepository: IBookingRepository,
    private val deviceRepository: IDeviceRepository,
) {

    operator fun invoke(page: Int, size: Int): Flux<BookingItem> =
        bookingRepository.findAll(page, size)
            .flatMap { booking -> Mono.just(booking).zipWith(deviceRepository.findByPublicId(booking.deviceId)) }
            .map { (booking, device) -> booking.toBookingItem(device.name, device.publicId) }
}