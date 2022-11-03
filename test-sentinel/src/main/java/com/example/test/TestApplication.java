package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = TestApplication.BASE_PACKAGE)
public class TestApplication {

  public static final String BASE_PACKAGE = "com.example";

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }

}
