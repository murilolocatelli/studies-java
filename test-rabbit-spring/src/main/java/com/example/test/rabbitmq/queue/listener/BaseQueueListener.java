package com.example.test.rabbitmq.queue.listener;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseQueueListener {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private String parkingLotQueueName() {
    return this.queueName() + ".parkingLot";
  }

  protected abstract String queueName();

  protected abstract Integer queueRetry();

  protected String getMessageBody(Message message) {
    return new String(message.getBody(), StandardCharsets.UTF_8);
  }

  protected <T> T stringToObject(final String jsonString, final Class<T> clazz) {
    return Optional.ofNullable(jsonString)
        .filter(t -> t.trim().length() > 0)
        .map(t -> {
          try {
            return objectMapper.readValue(jsonString, clazz);
          } catch (IOException e) {
            return null;
          }
        })
        .orElse(null);
  }

  protected boolean hasExceededRetryCount(Message message) {
    return this.getRetryCount(message).intValue() > this.queueRetry();
  }

  protected Long getRetryCount(Message message) {
    return Optional.ofNullable(message.getMessageProperties().getXDeathHeader())
        .filter(xDeathHeader -> xDeathHeader.size() >= 1)
        .map(xDeathHeader -> xDeathHeader.get(0).get("count"))
        .map(count -> (Long) count)
        .orElse(0L);
  }

  protected void logMessageReceived(final Message message, final String messageBody) {
    final String messageLog = MessageFormat.format(
        "Message received. Queue: {0} - Retry: {1} - Message: {2}",
        this.queueName(),
        this.getRetryCount(message),
        messageBody);

    log.info(messageLog);
  }

  protected void logMessageAck(final String messageBody) {
    final String messageLog = MessageFormat.format(
        "Message ack. Queue: {0} - Message: {1}",
        this.queueName(),
        messageBody);

    log.info(messageLog);
  }

  protected void putIntoParkingLot(final Message failedMessage, final String messageBody) {
    final String messageLog = MessageFormat.format(
        "Retries exceeded. Putting message into parking lot. Queue: {0} - Message: {1}",
        this.queueName(),
        messageBody);

    log.info(messageLog);

    this.rabbitTemplate.send(this.parkingLotQueueName(), failedMessage);
  }

}
