package com.picpay.credit.retrymanager.worker.consumer;

import java.util.function.Consumer;

import com.picpay.credit.retrymanager.common.consumer.BaseConsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RetryDlqConsumer extends BaseConsumer {

    @Bean
    public Consumer<Message<String>> retryDlq() {

        return message -> {
            String topicName = super.getTopicName(message);
            String payload = message.getPayload();

            log.info("Init consumer. Topic: {} - Payload: {}", topicName, payload);

            super.consume(message);

            log.info("Finish consumer. Topic: {} - Payload: {}", topicName, payload);
        };
    }

}
