package org.booking.booking

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
interface IBookingController {

    @GetMapping("/api/v1/device/booking")
    @ResponseBody
    fun bookingList(authentication: Authentication, @RequestParam size: Int?, @RequestParam page: Int?): Flux<BookingItem>

    @PostMapping("/api/v1/device/booking/{deviceId}")
    @ResponseBody
    fun book(authentication: Authentication, @PathVariable deviceId: String): Mono<BookingInfo>

    @PutMapping("/api/v1/device/booking/{deviceId}")
    @ResponseBody
    fun `return`(authentication: Authentication, @PathVariable deviceId: String): Mono<BookingInfo>
}

@Component
open class BookingController(
    private val bookingList: BookingQuery,
    private val bookDevice: BookDeviceCommand,
    private val returnDevice: ReturnDeviceCommand,
) : IBookingController {

    override fun bookingList(authentication: Authentication, size: Int?, page: Int?): Flux<BookingItem> =
        bookingList(page = page ?: 0, size = size ?: 10)

    override fun book(authentication: Authentication, deviceId: String): Mono<BookingInfo> =
        bookDevice(CreateBooking(authentication.principal.toString(), deviceId))

    override fun `return`(authentication: Authentication, deviceId: String): Mono<BookingInfo> =
        returnDevice(MakeReturning(authentication.principal.toString(), deviceId))
}