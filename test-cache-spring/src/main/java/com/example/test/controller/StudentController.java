package com.example.test.controller;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cache")
public class StudentController {

  @Autowired
  private StudentBusiness studentBusiness;

  @PostMapping("/add")
  @ResponseBody
  public ResponseEntity<Object> add(@RequestParam Integer start, @RequestParam Integer end) {
    Optional<StudentDto> studentDto = Optional.empty();

    for (int i = start; i <= end; i++) {
      studentDto = this.studentBusiness.get(String.valueOf(i));
    }

    return new ResponseEntity<>(studentDto, HttpStatus.OK);
  }

  @PostMapping("/evict")
  @ResponseBody
  public ResponseEntity<Object> evict(@RequestParam Integer start, @RequestParam Integer end) {
    Optional<StudentDto> studentDto = Optional.empty();

    for (int i = start; i <= end; i++) {
      studentDto = this.studentBusiness.evict(String.valueOf(i));
    }

    return new ResponseEntity<>(studentDto, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<Object> getById(@PathVariable String id) {

    return new ResponseEntity<>(this.studentBusiness.get(id), HttpStatus.OK);
  }

}
