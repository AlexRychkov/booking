package org.booking.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.booking.common.DEVICE_CREATED_TOPIC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class KafkaConfig {

    @Bean
    open fun deviceCreatedTopic(): NewTopic {
        return NewTopic(DEVICE_CREATED_TOPIC, 1, 1.toShort())
    }
}