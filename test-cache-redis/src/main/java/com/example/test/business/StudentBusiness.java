package com.example.test.business;

import com.example.test.dto.StudentDto;

import java.util.Optional;

public interface StudentBusiness {

    Optional<StudentDto> findById(String id);

}
