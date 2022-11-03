package com.example.test.rabbitmq.queue.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HermesWhatsAppQueueConfiguration extends BaseQueueConfiguration {

  @Value("${rabbitmq.queue.hermes-whatsApp.name}")
  private String queueName;

  @Value("${rabbitmq.queue.hermes-whatsApp.ttl}")
  private Integer queueTtl;

  @Override
  protected String queueName() {
    return this.queueName;
  }

  @Bean
  protected Queue hermesWhatsAppPrimaryQueue() {
    return QueueBuilder.durable(super.primaryQueueName())
        .deadLetterExchange("")
        .deadLetterRoutingKey(super.waitQueueName())
        .build();
  }

  @Bean
  protected Queue hermesWhatsAppWaitQueue() {
    return QueueBuilder.durable(super.waitQueueName())
        .deadLetterExchange("")
        .deadLetterRoutingKey(super.primaryQueueName())
        .ttl(this.queueTtl)
        .build();
  }

  @Bean
  protected Queue hermesWhatsAppParkingLotQueue() {
    return QueueBuilder.durable(super.parkingLotQueueName())
        .build();
  }

}
