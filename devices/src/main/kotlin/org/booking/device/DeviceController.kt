package org.booking.device

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
interface IDeviceController {

    @GetMapping("/api/v1/device")
    @ResponseBody
    fun deviceList(
        authentication: Authentication, @RequestParam size: Int?, @RequestParam page: Int?
    ): Flux<DeviceItem>

    @PostMapping("/api/v1/device")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    fun create(@RequestBody create: CreateDevice): Mono<DeviceItem>
}

@Component
open class DeviceController(
    private val deviceList: DeviceListQuery,
    private val createDevice: CreateDeviceCommand,
) : IDeviceController {

    override fun deviceList(
        authentication: Authentication,
        size: Int?,
        page: Int?,
    ): Flux<DeviceItem> = deviceList(page = page ?: 0, size = size ?: 10)

    override fun create(create: CreateDevice): Mono<DeviceItem> = createDevice(create)
}