package com.example.test.rabbitmq.queue.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HermesSmsQueueConfiguration extends BaseQueueConfiguration {

  @Value("${rabbitmq.queue.hermes-sms.name}")
  private String queueName;

  @Value("${rabbitmq.queue.hermes-sms.ttl}")
  private Integer queueTtl;

  @Override
  protected String queueName() {
    return this.queueName;
  }

  @Bean
  protected Queue hermesSmsPrimaryQueue() {
    return QueueBuilder.durable(super.primaryQueueName())
        .deadLetterExchange("")
        .deadLetterRoutingKey(super.waitQueueName())
        .build();
  }

  @Bean
  protected Queue hermesSmsWaitQueue() {
    return QueueBuilder.durable(super.waitQueueName())
        .deadLetterExchange("")
        .deadLetterRoutingKey(super.primaryQueueName())
        .ttl(this.queueTtl)
        .build();
  }

  @Bean
  protected Queue hermesSmsParkingLotQueue() {
    return QueueBuilder.durable(super.parkingLotQueueName())
        .build();
  }

}
