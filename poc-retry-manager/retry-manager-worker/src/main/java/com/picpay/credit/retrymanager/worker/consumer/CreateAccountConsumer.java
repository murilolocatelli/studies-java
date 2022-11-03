package com.picpay.credit.retrymanager.worker.consumer;

import java.util.function.Consumer;

import com.picpay.credit.retrymanager.common.consumer.BaseConsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CreateAccountConsumer extends BaseConsumer {

    //TODO: This consumer is for testing only. Should be removed for this project

    @Bean
    public Consumer<Message<String>> createAccount() {

        return message -> {
            String topicName = super.getTopicName(message);
        
            log.info("Init consumer. Topic: {} - Payload: {}", topicName, message.getPayload());

            if (message.getPayload().contains("Error")) {
                throw new RuntimeException("Intentional Exception");
            }

            log.info("Finish consumer. Topic: {} - Payload: {}", topicName, message.getPayload());
        };
    }

}
