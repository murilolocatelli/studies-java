package com.study.ecommerce.service;

import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

    public static void main(String[] args) {
        var logService = new LogService();

        try (var kafkaService =
                new KafkaService<String>(
                    Pattern.compile("ECOMMERCE.*"), LogService.class.getSimpleName(), logService.parse(),
                    Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<String>>> parse() {
        return (record) -> System.out
                .println("Log: " + record.key() + " | value: " + record.value() + " | topic: " + record.topic()
                        + " | partition: " + record.partition() + " | offset: " + record.offset());
    }

}
