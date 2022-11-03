package com.example.test.controller;

import com.example.test.business.StudentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class StudentController {

  @Autowired
  private StudentBusiness studentBusiness;

  //TODO: micrometer / prometheus
  //TODO: redis slave

  @PostMapping("/")
  @ResponseBody
  public ResponseEntity<String> post(@RequestParam String id, @RequestParam String name, @RequestParam Integer grade) {

    this.studentBusiness.save(id, name, grade);

    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<Object> get(@PathVariable String id) {

    return new ResponseEntity<>(this.studentBusiness.findById(id), HttpStatus.OK);
  }

}
