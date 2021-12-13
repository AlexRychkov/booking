package org.booking.device

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface IDeviceRepository : ReactiveCrudRepository<Device, DeviceId> {

    @Query("select * from device offset :offset limit :limit")
    fun findAll(offset: Int, limit: Int): Flux<Device>
}