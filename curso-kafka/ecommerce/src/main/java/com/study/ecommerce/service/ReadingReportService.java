package com.study.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

import com.study.ecommerce.dto.UserDto;
import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dto.MessageDto;
import com.study.ecommerce.util.IO;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ReadingReportService {

    private static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

    public static void main(String[] args) {
        var readingReportService = new ReadingReportService();

        try (var kafkaService =
                new KafkaService<UserDto>("ECOMMERCE_USER_GENERATE_READING_REPORT", "2" + ReadingReportService.class.getSimpleName(), readingReportService.parse())) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<UserDto>>> parse() {
        return (record) -> {
            System.out.println("Processing report for: " + record.key() + "  value: " + record.value() + " | topic: "
                    + record.topic() + " | partition: " + record.partition() + " | offset: " + record.offset());

            var message = record.value();
            var user = message.getPayload();

            var target = new File(user.getReportPath());

            try {
                IO.copyTo(SOURCE, target);
                IO.append(target, "Created for " + user.getUuid());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("File created: " + target.getAbsolutePath());
        };
    }

}
