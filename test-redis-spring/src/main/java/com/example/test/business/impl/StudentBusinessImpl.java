package com.example.test.business.impl;

import com.example.test.business.StudentBusiness;
import com.example.test.model.Student;
import com.example.test.repository.StudentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    @Autowired
    private StudentRepository studentRepository;

    public void save(String id, String name, Integer grade) {

        Student student = new Student(id, name, grade);

        this.studentRepository.save(student);
    }

    public Optional<Student> findById(String id) {

        return this.studentRepository.findById(id);
    }

}
