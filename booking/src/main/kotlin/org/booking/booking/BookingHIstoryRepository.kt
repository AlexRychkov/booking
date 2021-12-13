package org.booking.booking

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface IBookingRepository : ReactiveCrudRepository<Booking, BookingId> {

    @Query("select * from booking where device_id = :device_id order by time_at desc limit 1")
    fun findByDeviceIdAndByLastTimeAt(deviceId: String): Mono<Booking>

    @Query("select * from booking offset :offset limit :limit")
    fun findAll(offset: Int, limit: Int): Flux<Booking>
}