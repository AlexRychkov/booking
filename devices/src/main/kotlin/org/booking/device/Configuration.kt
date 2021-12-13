package org.booking.device

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactivefeign.webclient.WebReactiveFeign

@Configuration
open class Configuration {
    @Value("\${device.api.url}")
    private lateinit var apiUrl: String

    @Bean
    open fun deviceClient(): DeviceClient = WebReactiveFeign.builder<DeviceClient>()
        .target(DeviceClient::class.java, apiUrl)
}