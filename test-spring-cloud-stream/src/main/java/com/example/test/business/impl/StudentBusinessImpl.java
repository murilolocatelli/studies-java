package com.example.test.business.impl;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import com.example.test.business.StudentBusiness;
import com.example.test.configuration.MyBinder;
import com.example.test.dto.StudentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentBusinessImpl implements StudentBusiness {

    @Autowired
    private MyBinder myBinder;

    private int counter;

    @Override
    public void sendMessage(StudentDto studentDto) throws InterruptedException, ExecutionException {
        producer();
    }

    private void producer() {

        IntStream.range(0, 10).forEach(value -> {

            var message = String.format("TestString of %s - %s", counter, value);

            myBinder.orderOut().send(MessageBuilder.withPayload(message).build());

            log.info("message produced: {}", message);

        });
        counter++;
    }

}
