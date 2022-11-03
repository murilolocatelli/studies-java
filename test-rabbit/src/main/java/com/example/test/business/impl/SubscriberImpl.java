package com.example.test.business.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SubscriberImpl {

  private static final String QUEUE = "my-queue";
  private static final String EXCHANGE = "my-exchange";

  @EventListener(ApplicationReadyEvent.class)
  void load() {

    try {
      consumeMessageBindingRouteTopic();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void consumeMessage() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    boolean durable = true;

    var args = new HashMap<String, Object>();
    args.put("x-message-ttl", 300000);

    channel.queueDeclare(QUEUE, durable, false, false, args);

    int prefetchCount = 1;

    channel.basicQos(prefetchCount);

    DeliverCallback deliveryCallback = (consumerTag, delivery) -> {

      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

      if (message.equals("My message 1")) {

        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);

      } else {

        System.out.println(Thread.currentThread().getName() + " Message received: " + message);

        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      }
    };

    boolean autoAck = false;

    channel.basicConsume(QUEUE, autoAck, deliveryCallback, consumeMessage -> { });
  }

  private static void consumeMessageBinding() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE, "direct");

    final String queueName = channel.queueDeclare().getQueue();

    channel.queueBind(queueName, EXCHANGE, "");

    int prefetchCount = 1;

    channel.basicQos(prefetchCount);

    DeliverCallback deliveryCallback = (consumerTag, delivery) -> {

      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

      if (message.equals("My message 1")) {

        try {
          Thread.sleep(50000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        //throw new RuntimeException("test");
      }

      System.out.println(Thread.currentThread().getName() + " Message received: " + message);

      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    };

    boolean autoAck = false;

    channel.basicConsume(queueName, autoAck, deliveryCallback, consumeMessage -> { });
  }

  private static void consumeMessageBindingRoute() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE, "direct");

    final String queueName = channel.queueDeclare().getQueue();

    channel.queueBind(queueName, EXCHANGE, "odd");

    int prefetchCount = 1;

    channel.basicQos(prefetchCount);

    DeliverCallback deliveryCallback = (consumerTag, delivery) -> {

      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

      if (message.equals("My message 1")) {

        try {
          Thread.sleep(20000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        //throw new RuntimeException("test");
      }

      System.out.println(Thread.currentThread().getName() + " Message received: " + message);

      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    };

    boolean autoAck = false;

    channel.basicConsume(queueName, autoAck, deliveryCallback, consumeMessage -> { });
  }

  private static void consumeMessageBindingRouteTopic() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE, "topic");

    final String queueName = channel.queueDeclare().getQueue();

    channel.queueBind(queueName, EXCHANGE, "*.5");

    int prefetchCount = 1;

    channel.basicQos(prefetchCount);

    DeliverCallback deliveryCallback = (consumerTag, delivery) -> {

      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

      if (message.equals("My message 1")) {

        try {
          Thread.sleep(20000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        //throw new RuntimeException("test");
      }

      System.out.println(Thread.currentThread().getName() + " Message received: " + message);

      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    };

    boolean autoAck = false;

    channel.basicConsume(queueName, autoAck, deliveryCallback, consumeMessage -> { });
  }

}
