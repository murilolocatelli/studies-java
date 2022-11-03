package com.example.test.controller;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cache/redis")
public class StudentController {

  @Autowired
  private StudentBusiness studentBusiness;

  @PostMapping("/add")
  @ResponseBody
  public ResponseEntity<Object> add(@RequestParam Integer start, @RequestParam Integer end) {
    Optional<StudentDto> studentDto = Optional.empty();

    for (int i = start; i <= end; i++) {
      studentDto = this.studentBusiness.findById(String.valueOf(i));
    }

    return new ResponseEntity<>(studentDto, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<Object> get(@PathVariable String id) {

    Optional<StudentDto> studentDto = this.studentBusiness.findById(id);

    return new ResponseEntity<>(studentDto, HttpStatus.OK);
  }

}
