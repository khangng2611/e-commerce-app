package com.khangng.payment_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPaymentTopicConfig {
    @Bean
    public NewTopic createPaymentTopic() {
        return TopicBuilder.name("payment-topic").build();
    }
}
