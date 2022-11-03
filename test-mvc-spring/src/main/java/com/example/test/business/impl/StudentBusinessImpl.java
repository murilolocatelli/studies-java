package com.example.test.business.impl;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.stereotype.Service;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    public StudentDto getById2(String id) {
        return new StudentDto("1", "name");
    }

}
