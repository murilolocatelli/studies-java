package com.example.test.business.impl;

import java.util.concurrent.ExecutionException;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;

import org.springframework.stereotype.Service;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    @Override
    public void sendMessage(StudentDto studentDto) throws InterruptedException, ExecutionException {
        
    }

}
