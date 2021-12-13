package org.booking.device

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface IDeviceRepository : ReactiveCrudRepository<Device, DeviceId> {

    fun findByPublicId(publicId: String): Mono<Device>
}