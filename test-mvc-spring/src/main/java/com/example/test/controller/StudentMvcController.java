package com.example.test.controller;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students-mvc")
public class StudentMvcController {

  @Autowired
  private StudentBusiness studentBusiness;

  @GetMapping(value = "/{id}")
  private ResponseEntity<StudentDto> getById(@PathVariable String id) {
    System.out.println("Thread = " + Thread.currentThread().getName());

    return new ResponseEntity<>(this.studentBusiness.getById2(id), HttpStatus.OK);
  }

}
