package com.example.test.business.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.test.business.Publisher;
import com.example.test.dto.HermesDto;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PublisherImpl implements Publisher {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Value("${rabbitmq.exchange.hermes.name}")
  private String exchangeHermesName;

  @Override
  public void publishMessage() {

    for (int i = 1; i <= 500; i++) {

      final HermesDto hermesDto = HermesDto.builder()
          .id(i)
          .description("Description " + i)
          .build();

      this.rabbitTemplate.convertAndSend(
          this.exchangeHermesName, "sms.whatsApp", this.objectToString(hermesDto));
    }
  }

  private String objectToString(final Object object) {
    return Optional.ofNullable(object)
        .map(t -> {
          try {
            return objectMapper.writeValueAsString(object);
          } catch (JsonProcessingException e) {
            return null;
          }
        })
        .orElse(null);
  }

}
