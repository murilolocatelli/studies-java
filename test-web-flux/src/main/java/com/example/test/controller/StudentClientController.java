package com.example.test.controller;

import com.example.test.dto.StudentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class StudentClientController {

  private final WebClient client = WebClient.create("http://localhost:8080");

  @GetMapping("/{id}")
  private void getById(@PathVariable String id) {

    Mono<StudentDto> mono = client.get()
            .uri("/students-functional/{id}", id)
            .retrieve()
            .bodyToMono(StudentDto.class);

    mono.subscribe(System.out::println);
  }

  @GetMapping
  private void getAll() {

    Flux<StudentDto> flux = client.get()
            .uri("/students-functional")
            .retrieve()
            .bodyToFlux(StudentDto.class);

    flux.subscribe(System.out::println);
  }

}
