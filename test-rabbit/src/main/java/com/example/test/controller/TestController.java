package com.example.test.controller;

import com.example.test.business.Publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private Publisher publisher;

  @GetMapping("/get")
  @ResponseBody
  public ResponseEntity<String> get() {

    System.out.println("Thread Name Controller: " + Thread.currentThread().getName());

    this.publisher.sendMessageParallel("My message ");

    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

}
