package org.booking.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.booking"])
open class AuthApp

fun main(args: Array<String>) {
    runApplication<AuthApp>(*args)
}