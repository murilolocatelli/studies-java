package com.example.test.rabbitmq.queue.listener;

import com.example.test.business.HermesWhatsAppBusiness;
import com.example.test.dto.HermesDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HermesWhatsAppQueueListener extends BaseQueueListener {

  @Autowired
  private HermesWhatsAppBusiness hermesWhatsAppBusiness;

  @Value("${rabbitmq.queue.hermes-whatsApp.name}")
  private String queueName;

  @Value("${rabbitmq.queue.hermes-whatsApp.retry}")
  private Integer queueRetry;

  @Override
  protected String queueName() {
    return this.queueName;
  }

  @Override
  protected Integer queueRetry() {
    return this.queueRetry;
  }

  @RabbitListener(queues = "${rabbitmq.queue.hermes-whatsApp.name}")
  protected void listenerQueue(Message message) {

    String messageBody = super.getMessageBody(message);

    super.logMessageReceived(message, messageBody);

    if (super.hasExceededRetryCount(message)) {
      super.putIntoParkingLot(message, messageBody);
      return;
    }

    HermesDto hermesDto = super.stringToObject(messageBody, HermesDto.class);

    this.hermesWhatsAppBusiness.execute(hermesDto);

    super.logMessageAck(messageBody);
  }

}
