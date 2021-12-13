package org.booking.device

import feign.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.lang.IllegalStateException

data class SearchBody(
    val category: String = "all"
)

interface DeviceClient {

    @RequestLine("POST /api/product/search")
    @Headers(
        "x-blobr-key: {apiKey}",
        "Accept: application/json",
        "Content-Type: application/json",
    )
    fun findByName(
        body: SearchBody,
        @QueryMap params: Map<String, String>,
        @Param("apiKey") apiKey: String
    ): Mono<DeviceSearchApiItem>

    @RequestLine("GET api/product/get/{id}")
    @Headers(
        "x-blobr-key: {apiKey}",
        "Accept: application/json",
    )
    fun detailsById(
        @Param("id") id: String,
        @Param("apiKey") apiKey: String
    ): Mono<DeviceApiItem>
}

interface IDeviceApiRepository {
    fun findByName(name: String): Mono<DeviceApiItem>
}

@Repository
open class DeviceApiRepository(
    private val client: DeviceClient,
    @Value("\${device.api.primaryApiKey}")
    private val apiKey: String
) : IDeviceApiRepository {
    private val searchBody = SearchBody()

    override fun findByName(name: String): Mono<DeviceApiItem> {
        val params = mapOf("query" to name)
        return client.findByName(searchBody, params, apiKey)
            .map { device -> device.data.results.maxByOrNull { it.score } }
            .switchIfEmpty(Mono.error(IllegalStateException("Unexpected error happened")))
            .flatMap { searchResult -> client.detailsById(searchResult!!._meta.id, apiKey) }
    }
}