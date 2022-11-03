package com.picpay.credit.retrymanager.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = RetryManagerWorker.BASE_PACKAGE)
@EnableMongoRepositories(basePackages = RetryManagerWorker.BASE_PACKAGE_REPOSITORIES)
//@EnableBinding(value = {Binder.class})
@EnableCaching
@EnableScheduling
public class RetryManagerWorker {

    public static final String BASE_PACKAGE = "com.picpay.credit";
    public static final String BASE_PACKAGE_REPOSITORIES = "com.picpay.credit.retrymanager.common.repository";

    public static void main(String[] args) {
        SpringApplication.run(RetryManagerWorker.class, args);
    }

}
