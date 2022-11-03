package com.example.test.business;

import java.util.concurrent.ExecutionException;

import com.example.test.dto.StudentDto;

public interface StudentBusiness {

    void sendMessage(StudentDto studentDto) throws InterruptedException, ExecutionException;

}
