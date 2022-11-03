package com.study.ecommerce.service;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import com.study.ecommerce.dto.EmailDto;
import com.study.ecommerce.dto.OrderDto;
import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dispatcher.KafkaDispatcher;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailNewOrderService {
    
    private final KafkaDispatcher<EmailDto> dispatcher = new KafkaDispatcher<>();

    public static void main(String[] args) {
        var EmailNewOrderService = new EmailNewOrderService();

        try (var kafkaService =
                new KafkaService<OrderDto>("ECOMMERCE_NEW_ORDER", EmailNewOrderService.class.getSimpleName(), EmailNewOrderService.parse())) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<OrderDto>>> parse() {
        return (record) -> {
            System.out.println("Processing new order, preparing email: " + record.key() + "  value: " + record.value() + " | topic: "
                    + record.topic() + " | partition: " + record.partition() + " | offset: " + record.offset());

            var message = record.value();

            String body = "We are processing your order";
            var emailDto = new EmailDto("subject", body);

            try {
                dispatcher.send(
                    "ECOMMERCE_SEND_MAIL", message.getPayload().getEmail(),
                    message.getId().continueWith(EmailNewOrderService.class.getSimpleName()), emailDto);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
    }
    
}
