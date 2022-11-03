package com.study.ecommerce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.study.ecommerce.dto.UserDto;
import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dispatcher.KafkaDispatcher;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class BatchSendMessageService {

    private final Connection connection;

    private final KafkaDispatcher<UserDto> dispatcher = new KafkaDispatcher<>();

    public BatchSendMessageService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db";
        this.connection = DriverManager.getConnection(url);

        try {
            this.connection.createStatement()
                    .execute("create table users (uuid varchar(200) primary key, email varchar (200))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        var batchSendMessageService = new BatchSendMessageService();

        try (var kafkaService = new KafkaService<String>("ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS",
                BatchSendMessageService.class.getSimpleName(), batchSendMessageService.parse())) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<String>>> parse() {
        return (record) -> {
            System.out.println("Processing new batch: " + record.key() + "  value: " + record.value() + " | topic: "
                    + record.topic() + " | partition: " + record.partition() + " | offset: " + record.offset());

            var message = record.value();
            var payload = message.getPayload();

            try {
                for (UserDto user : getAllUsers()) {
                    dispatcher.sendAsync(payload, user.getUuid(), message.getId().continueWith(BatchSendMessageService.class.getSimpleName()), user);

                    System.out.println("Enviei para o user " + user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private List<UserDto> getAllUsers() throws SQLException {
        var results = this.connection.prepareStatement("select uuid from users").executeQuery();

        List<UserDto> users = new ArrayList<>();
        while (results.next()) {
            users.add(new UserDto(results.getString(1)));
        }

        return users;
    }

}