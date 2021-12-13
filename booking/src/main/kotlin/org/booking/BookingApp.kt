package org.booking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.booking"])
open class BookingApp

fun main(args: Array<String>) {
    runApplication<BookingApp>(*args)
}