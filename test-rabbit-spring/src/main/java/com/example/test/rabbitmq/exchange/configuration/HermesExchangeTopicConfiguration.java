package com.example.test.rabbitmq.exchange.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HermesExchangeTopicConfiguration {

  @Value("${rabbitmq.exchange.hermes.name}")
  private String exchangeHermesName;

  @Value("${rabbitmq.queue.hermes-sms.name}")
  private String queueHermesSmsName;

  @Value("${rabbitmq.queue.hermes-whatsApp.name}")
  private String queueHermesWhatsAppName;

  private static final String HERMES_SMS_BINDING = "#.sms.#";
  private static final String HERMES_WHATSAPP_BINDING = "#.whatsApp.#";

  @Bean
  protected TopicExchange hermesExchange() {
    return new TopicExchange(this.exchangeHermesName);
  }

  @Bean
  Binding hermesSmsBinding() {
    return new Binding(
        this.queueHermesSmsName,
        Binding.DestinationType.QUEUE,
        this.exchangeHermesName,
        HERMES_SMS_BINDING,
        null);
  }

  @Bean
  Binding hermesWhatsAppBinding() {
    return new Binding(
        this.queueHermesWhatsAppName,
        Binding.DestinationType.QUEUE,
        this.exchangeHermesName,
        HERMES_WHATSAPP_BINDING,
        null);
  }

}
