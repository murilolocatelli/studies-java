package com.example.test.business;

import com.example.test.dto.StudentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentBusiness {

    Mono<StudentDto> getById(String id);

    Flux<StudentDto> getAll();

}
