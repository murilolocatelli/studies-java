package com.example.test.business;

import com.example.test.dto.StudentDto;

import java.util.Optional;

public interface StudentBusiness {

    Optional<StudentDto> get(String id);

    Optional<StudentDto> evict(String id);

}
