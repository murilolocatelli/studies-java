package com.picpay.credit.retrymanager.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = RetryManagerScheduler.BASE_PACKAGE)
@EnableMongoRepositories(basePackages = RetryManagerScheduler.BASE_PACKAGE_REPOSITORIES)
@EnableScheduling
public class RetryManagerScheduler {

    public static final String BASE_PACKAGE = "com.picpay.credit";
    public static final String BASE_PACKAGE_REPOSITORIES = "com.picpay.credit.retrymanager.common.repository";

    public static void main(String[] args) {
        SpringApplication.run(RetryManagerScheduler.class, args);
    }

}
