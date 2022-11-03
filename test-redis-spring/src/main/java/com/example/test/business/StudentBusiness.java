package com.example.test.business;

import com.example.test.model.Student;
import java.util.Optional;

public interface StudentBusiness {

    void save(String id, String name, Integer grade);

    Optional<Student> findById(String id);

}
