package org.booking.http

import org.booking.BookedBySomeoneElse
import org.booking.DeviceInUse
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.format.DateTimeParseException

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler : ErrorWebExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        when (ex) {
            is DeviceInUse ->
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
            is BookedBySomeoneElse ->
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
            is IllegalArgumentException ->
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
            is DateTimeParseException ->
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
            is org.springframework.security.access.AccessDeniedException ->
                exchange.response.statusCode = HttpStatus.FORBIDDEN
            else ->
                exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
        logger.warn("Error occurred: ", ex)
        return exchange.response.setComplete()
    }
}
