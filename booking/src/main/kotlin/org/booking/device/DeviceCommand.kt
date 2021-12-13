package org.booking.device

import org.booking.booking.Booking
import org.booking.booking.BookingStatus.FREE
import org.booking.booking.IBookingRepository
import org.booking.common.ACommandHandler
import org.booking.common.Event.*
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Component
class CreateDeviceCommand(
    private val deviceRepository: IDeviceRepository,
    private val bookingRepository: IBookingRepository,
    private val operator: TransactionalOperator,
) : ACommandHandler<DeviceCreated, Mono<Void>>() {

    override fun validate(payload: DeviceCreated) {}

    override fun processInternal(payload: DeviceCreated): Mono<Void> =
        deviceRepository.save(payload.toDevice())
            .flatMap { device -> bookingRepository.save(Booking(deviceId = device.publicId, status = FREE)) }
            .`as`(operator::transactional)
            .then()
}