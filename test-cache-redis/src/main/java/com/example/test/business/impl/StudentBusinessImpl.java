package com.example.test.business.impl;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    @Cacheable("student")
    public Optional<StudentDto> findById(String id) {
        System.out.println("id = " + id);

        return Optional.of(new StudentDto(id, "Name " + id));
    }

}
