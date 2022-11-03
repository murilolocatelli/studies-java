package com.example.test.controller;

import com.example.test.business.TestBusiness;

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
  private TestBusiness testBusiness;

  @GetMapping("/get")
  @ResponseBody
  public ResponseEntity<String> get(final Long timeout) {

    System.out.println("Thread Name Controller: " + Thread.currentThread().getName());

    return new ResponseEntity<>(this.testBusiness.callService(timeout), HttpStatus.OK);
  }

}
