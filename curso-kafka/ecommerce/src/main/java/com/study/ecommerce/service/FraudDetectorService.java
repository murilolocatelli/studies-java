package com.study.ecommerce.service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import com.study.ecommerce.dto.OrderDto;
import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dispatcher.KafkaDispatcher;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {

    private final KafkaDispatcher<OrderDto> dispatcher = new KafkaDispatcher<>();

    public static void main(String[] args) {
        var fraudDetectorService = new FraudDetectorService();

        try (var kafkaService =
                new KafkaService<OrderDto>("ECOMMERCE_NEW_ORDER", FraudDetectorService.class.getSimpleName(), fraudDetectorService.parse())) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<OrderDto>>> parse() {
        return (record) -> {
            System.out.println("Processing new order: " + record.key() + "  value: " + record.value() + " | topic: "
                    + record.topic() + " | partition: " + record.partition() + " | offset: " + record.offset());

            var message = record.value();
            var orderDto = message.getPayload();

            if (this.isFraud(orderDto)) {
                System.out.println("Order is a fraud: " + orderDto );
                try {
                    dispatcher.send("ECOMMERCE_ORDER_REJECTED", orderDto.getEmail(), message.getId().continueWith(FraudDetectorService.class.getSimpleName()), orderDto);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Order approved: " + orderDto);
                try {
                    dispatcher.send("ECOMMERCE_ORDER_APPROVED", orderDto.getEmail(), message.getId().continueWith(FraudDetectorService.class.getSimpleName()), orderDto);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private boolean isFraud(OrderDto orderDto) {
        return orderDto.getAmount().compareTo(new BigDecimal("4500")) >= 0;
    }

}
