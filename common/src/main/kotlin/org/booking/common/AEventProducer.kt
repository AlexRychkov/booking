package org.booking.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import reactor.core.publisher.Mono

abstract class AEventProducer<E>(
    private val topic: String
) {
    @Autowired
    private lateinit var template: KafkaTemplate<*, E>

    operator fun invoke(event: E): Mono<Void> = Mono.just(template.send(topic, event)).then()
}