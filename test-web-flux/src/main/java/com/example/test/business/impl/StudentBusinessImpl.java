package com.example.test.business.impl;

import com.example.test.business.StudentBusiness;
import com.example.test.dto.StudentDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentBusinessImpl implements StudentBusiness {

    public Mono<StudentDto> getById(String id) {
        return Mono.create(callback -> {
            callback.success(new StudentDto("1", "name"));
        });
    }

    public Flux<StudentDto> getAll() {
        /*List<StudentDto> students = new ArrayList<>();
        students.add(new StudentDto("2", "name"));
        students.add(new StudentDto("3", "name"));
        students.add(new StudentDto("4", "name"));
        students.add(new StudentDto("5", "name"));
        students.add(new StudentDto("6", "name"));

        return Flux.fromIterable(students).delayElements(Duration.ofSeconds (3));*/

        return Flux.create(callback -> {
            this.sleep();
            callback.next(new StudentDto("2", "name"));

            this.sleep();
            callback.next(new StudentDto("3", "name"));

            this.sleep();
            callback.next(new StudentDto("4", "name"));

            callback.complete();
        });
    }

    private void sleep() {
        int seconds = 3;
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
