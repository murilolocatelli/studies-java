package com.example.test.configuration;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MyConsumer {

    @StreamListener(MyBinder.ORDER_IN)
    public void consumer(@Payload String message) {
        log.info("message consumed: {}", message);

        throw new RuntimeException("Test RuntimeException");
    }

}
