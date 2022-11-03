package com.example.test.business.impl;

import java.util.concurrent.ExecutionException;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    @Autowired
    private KafkaTemplate<String, StudentDto> kafkaTemplate;

    @Override
    public void sendMessage(StudentDto studentDto) throws InterruptedException, ExecutionException {
        this.kafkaTemplate.send("myTopic", studentDto).get();
    }

}
