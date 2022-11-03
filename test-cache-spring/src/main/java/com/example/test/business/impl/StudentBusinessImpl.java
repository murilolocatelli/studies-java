package com.example.test.business.impl;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    @Cacheable("student")
    public Optional<StudentDto> get(String id) {
        System.out.println("id = " + id);

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return Optional.of(new StudentDto(id, "Name " + id));
    }

    @CacheEvict("student")
    public Optional<StudentDto> evict(String id) {
        System.out.println("Evict id = " + id);

        return null;
    }

}
