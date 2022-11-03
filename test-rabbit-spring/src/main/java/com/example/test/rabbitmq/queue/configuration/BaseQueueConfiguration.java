package com.example.test.rabbitmq.queue.configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseQueueConfiguration {

  protected String primaryQueueName() {
    return this.queueName();
  }

  protected String waitQueueName() {
    return this.primaryQueueName() + ".wait";
  }

  protected String parkingLotQueueName() {
    return this.primaryQueueName() + ".parkingLot";
  }

  protected abstract String queueName();

}
