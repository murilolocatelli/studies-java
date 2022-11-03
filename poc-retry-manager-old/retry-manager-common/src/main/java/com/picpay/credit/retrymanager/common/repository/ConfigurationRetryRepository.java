package com.picpay.credit.retrymanager.common.repository;

import java.util.Optional;

import com.picpay.credit.retrymanager.common.model.ConfigurationRetry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRetryRepository extends MongoRepository<ConfigurationRetry, String> {

    Optional<ConfigurationRetry> findByTopicDlq(String topicDlq);
    
}
