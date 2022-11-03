package com.study.ecommerce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.study.ecommerce.dto.OrderDto;
import com.study.ecommerce.kafka.consumer.KafkaService;
import com.study.ecommerce.kafka.dto.MessageDto;

public class CreateUserService {

    private final Connection connection;

    public CreateUserService() throws SQLException {
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
        var createUserService = new CreateUserService();

        try (var kafkaService = new KafkaService<OrderDto>("ECOMMERCE_NEW_ORDER",
                CreateUserService.class.getSimpleName(), createUserService.parse())) {

            kafkaService.run();
        }
    }

    public Consumer<ConsumerRecord<String, MessageDto<OrderDto>>> parse() {
        return (record) -> {
            System.out.println("Processing new user: " + record.key() + "  value: " + record.value() + " | topic: "
                    + record.topic() + " | partition: " + record.partition() + " | offset: " + record.offset());

            var message = record.value();
            var orderDto = message.getPayload();

            try {
                if (isNewUser(orderDto.getEmail())) {
                    this.insertNewUser(orderDto);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

    private void insertNewUser(OrderDto orderDto) throws SQLException {
        var insert = this.connection.prepareStatement("insert into users (uuid, email) values (?, ?)");
        insert.setString(1, UUID.randomUUID().toString());
        insert.setString(2, orderDto.getEmail());
        insert.execute();

        System.out.println("Usuário adicionado: " + orderDto.getEmail());
    }

    private boolean isNewUser(String email) throws SQLException {
        var exists = this.connection.prepareStatement("select uuid from users where email = ? limit 1");
        exists.setString(1, email);

        var result = exists.executeQuery();

        return !result.next();
    }

}
