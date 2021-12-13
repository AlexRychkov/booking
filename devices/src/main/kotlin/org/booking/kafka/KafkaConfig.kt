package org.booking.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.booking.common.DEVICE_CREATED_TOPIC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.util.backoff.FixedBackOff

@Configuration
open class KafkaConfig {

    @Bean
    open fun deviceCreatedTopic(): NewTopic {
        return NewTopic(DEVICE_CREATED_TOPIC, 1, 1.toShort())
    }

    @Bean
    open fun errorHandler(template: KafkaOperations<*, *>): SeekToCurrentErrorHandler {
        return SeekToCurrentErrorHandler(DeadLetterPublishingRecoverer(template), FixedBackOff(1000L, 2))
    }
}