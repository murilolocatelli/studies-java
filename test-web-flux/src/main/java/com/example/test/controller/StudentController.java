package com.example.test.controller;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {

  @Autowired
  private StudentBusiness studentBusiness;

  @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  private Mono<StudentDto> getById(@PathVariable String id) {
    System.out.println("Thread = " + Thread.currentThread().getName());

    return this.studentBusiness.getById(id);
  }

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  private Flux<StudentDto> getAll() {

    Flux<StudentDto> flux = this.studentBusiness.getAll();

    System.out.println("end controller.getAll()");

    return flux;
  }

}
