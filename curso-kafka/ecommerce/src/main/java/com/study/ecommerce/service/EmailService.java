package com.study.ecommerce.service;

import java.util.function.Consumer;

import com.study.ecommerce.dto.EmailDto;
import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();

        try (var kafkaService =
                new KafkaService<EmailDto>("ECOMMERCE_SEND_MAIL", EmailService.class.getSimpleName(), emailService.parse())) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<EmailDto>>> parse() {
        return (record) -> System.out
                .println("Email sent: " + record.key() + " | value: " + record.value() + " | topic: " + record.topic()
                        + " | partition: " + record.partition() + " | offset: " + record.offset());
    }

}
