package com.picpay.credit.retrymanager.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = RetryManagerApi.BASE_PACKAGE)
@EnableMongoRepositories(basePackages = RetryManagerApi.BASE_PACKAGE_REPOSITORIES)
public class RetryManagerApi {

    public static final String BASE_PACKAGE = "com.picpay.credit";
    public static final String BASE_PACKAGE_REPOSITORIES = "com.picpay.credit.retrymanager.common.repository";

    public static void main(String[] args) {
        SpringApplication.run(RetryManagerApi.class, args);
    }

}
