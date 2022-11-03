package com.example.test.business.impl;

import com.example.test.business.Publisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

@Service
public class PublisherImpl implements Publisher {

  private static final String QUEUE = "my-queue";
  private static final String EXCHANGE = "my-exchange";

  public void sendMessageParallel(String message) {
    CompletableFuture<Void> future1 =
        CompletableFuture.runAsync(() -> sendMessageToQueue(message));
    /*CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future5 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future6 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future7 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future8 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future9 = CompletableFuture.runAsync(() -> sendMessage(message));
    CompletableFuture<Void> future10 = CompletableFuture.runAsync(() -> sendMessage(message));*/

    /*CompletableFuture<Void> future = CompletableFuture.allOf(
        future1, future2, future3, future4, future5,
        future6, future7, future8, future9, future10);*/

    CompletableFuture<Void> future = CompletableFuture.allOf(future1);

    try {
      future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  private void sendMessageToQueue(String message) {

    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");

      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      boolean durable = true;

      var args = new HashMap<String, Object>();
      args.put("x-message-ttl", 300000);

      channel.queueDeclare(QUEUE, durable, false, false, args);

      for (int i = 1; i <= 10; i++) {

        String messageIncrement = message + i;

        channel.basicPublish(
            "",
            QUEUE,
            MessageProperties.PERSISTENT_TEXT_PLAIN,
            messageIncrement.getBytes(StandardCharsets.UTF_8));

        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      channel.close();
      connection.close();

    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  private void sendMessageToExchangeFanout(String message) {

    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");

      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      channel.exchangeDeclare(EXCHANGE, "fanout");

      for (int i = 1; i <= 10; i++) {

        String messageIncrement = message + i;

        channel.basicPublish(
            EXCHANGE,
            "",
            MessageProperties.PERSISTENT_TEXT_PLAIN,
            messageIncrement.getBytes(StandardCharsets.UTF_8));

        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      channel.close();
      connection.close();

    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  private void sendMessageToExchangeDirect(String message) {

    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");

      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      channel.exchangeDeclare(EXCHANGE, "direct");

      for (int i = 1; i <= 10; i++) {

        String messageIncrement = message + i;

        channel.basicPublish(
            EXCHANGE,
            i % 2 == 0 ? "even" : "odd",
            MessageProperties.PERSISTENT_TEXT_PLAIN,
            messageIncrement.getBytes(StandardCharsets.UTF_8));

        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      channel.close();
      connection.close();

    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  private void sendMessageToExchangeTopic(String message) {

    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");

      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      channel.exchangeDeclare(EXCHANGE, "topic");

      for (int i = 1; i <= 10; i++) {

        String messageIncrement = message + i;

        channel.basicPublish(
            EXCHANGE,
            i % 2 == 0 ? "even" : "odd" + "." + i,
            MessageProperties.PERSISTENT_TEXT_PLAIN,
            messageIncrement.getBytes(StandardCharsets.UTF_8));

        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      channel.close();
      connection.close();

    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

}
