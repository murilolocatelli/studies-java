package com.example.test.controller;

import java.util.Base64;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
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

    @Autowired
    private AmazonS3 amazonS3;

    @GetMapping(value = "/{id}")
    private ResponseEntity<StudentDto> getById(@PathVariable String id) throws Exception {
        System.out.println("Thread = " + Thread.currentThread().getName());

        StudentDto studentDto = new StudentDto();

        S3Object s3Object = amazonS3.getObject("test-bucket-murilo", "tasks.txt");

        byte[] contentBytes = s3Object.getObjectContent().readAllBytes();

        System.out.println(Base64.getEncoder().encodeToString(contentBytes));

        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<StudentDto> post(@RequestBody StudentDto studentDto) throws InterruptedException, ExecutionException {
        System.out.println("Thread = " + Thread.currentThread().getName());

        this.studentBusiness.sendMessage(studentDto);

        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

}
