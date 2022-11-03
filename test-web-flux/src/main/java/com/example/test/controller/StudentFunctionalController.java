package com.example.test.controller;

import com.example.test.business.StudentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class StudentFunctionalController {

  @Autowired
  private StudentBusiness studentBusiness;

  /*@Bean
  RouterFunction<ServerResponse> getById() {
    return route(GET("/students-functional/{id}"),
            req -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(this.studentBusiness.getById(req.pathVariable("id")), Mono.class));
  }

  @Bean
  RouterFunction<ServerResponse> getAll() {
    return route(GET("/students-functional"),
            req -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(this.studentBusiness.getAll(), Flux.class));
  }*/

  @Bean
  RouterFunction<ServerResponse> composedRoutes() {
    return route(GET("/students-functional/{id}"),
            req -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(this.studentBusiness.getById(req.pathVariable("id")), Mono.class))

            .and(route(GET("/students-functional"),
                    req -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
                            .body(this.studentBusiness.getAll(), Flux.class)));
  }

}
