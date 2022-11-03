package com.example.test;

import com.example.test.configuration.MyBinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication(scanBasePackages = TestApplication.BASE_PACKAGE)
@EnableBinding(value = {MyBinder.class})
public class TestApplication {

    public static final String BASE_PACKAGE = "com.example";

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
