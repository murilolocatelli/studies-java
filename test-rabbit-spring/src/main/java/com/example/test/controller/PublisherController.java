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
@RequestMapping("/publisher")
public class PublisherController {

  @Autowired
  private Publisher publisher;

  @GetMapping("/")
  @ResponseBody
  public ResponseEntity<String> home() {

    this.publisher.publishMessage();

    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

}
