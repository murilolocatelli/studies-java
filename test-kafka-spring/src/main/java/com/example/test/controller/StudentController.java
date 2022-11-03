package com.example.test.controller;

import java.util.concurrent.ExecutionException;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

    @Autowired
    private StudentBusiness studentBusiness;

    @GetMapping(value = "/{id}")
    private ResponseEntity<StudentDto> getById(@PathVariable String id) {
        System.out.println("Thread = " + Thread.currentThread().getName());

        StudentDto studentDto = new StudentDto();

        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<StudentDto> post(@RequestBody StudentDto studentDto) throws InterruptedException, ExecutionException {
        System.out.println("Thread = " + Thread.currentThread().getName());

        this.studentBusiness.sendMessage(studentDto);

        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

}
