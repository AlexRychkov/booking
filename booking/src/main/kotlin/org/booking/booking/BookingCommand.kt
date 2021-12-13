package org.booking.booking

import org.booking.BookedBySomeoneElse
import org.booking.DeviceInUse
import org.booking.common.ACommandHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Component
class BookDeviceCommand(
    private val bookingRepository: IBookingRepository,
) : ACommandHandler<CreateBooking, Mono<BookingInfo>>() {

    override fun validate(payload: CreateBooking) {}

    override fun processInternal(payload: CreateBooking): Mono<BookingInfo> =
        bookingRepository.findByDeviceIdAndByLastTimeAt(payload.deviceId)
            .filter { booking -> booking.status == BookingStatus.FREE }
            .switchIfEmpty(Mono.error(DeviceInUse()))
            .map { booking -> booking.copy(status = BookingStatus.BOOKED, timeAt = LocalDateTime.now(), userId = payload.userId) }
            .flatMap { newBooking -> bookingRepository.save(newBooking) }
            .map { it.toBookingInfo() }
}

@Component
class ReturnDeviceCommand(
    private val bookingRepository: IBookingRepository,
) : ACommandHandler<MakeReturning, Mono<BookingInfo>>() {

    override fun validate(payload: MakeReturning) {}

    override fun processInternal(payload: MakeReturning): Mono<BookingInfo> =
        bookingRepository.findByDeviceIdAndByLastTimeAt(payload.deviceId)
            .filter { booking -> booking.status == BookingStatus.BOOKED }
            .switchIfEmpty(Mono.error(IllegalStateException("Device not in use")))
            .filter { booking -> booking.userId == payload.userId }
            .switchIfEmpty(Mono.error(BookedBySomeoneElse()))
            .map { booking -> booking.copy(status = BookingStatus.FREE, timeAt = LocalDateTime.now(), userId = payload.userId) }
            .flatMap { newBooking -> bookingRepository.save(newBooking) }
            .map { it.toBookingInfo() }
}