package com.picpay.credit.retrymanager.common.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.picpay.credit.retrymanager.common.model.MessageRetry;
import com.picpay.credit.retrymanager.common.model.MessageRetryStatus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRetryRepository extends MongoRepository<MessageRetry, String> {

    @Query(value = "{'feature': ?0, 'status': ?1, 'executionTime': {$lt: ?2}}", sort = "{'createdAt': 1}")
    List<MessageRetry> findMessagesRetry(String feature, MessageRetryStatus status, LocalDateTime executionTime);

    Optional<MessageRetry> findFirstByMessageHashCodeOrderByAttemptDesc(Integer messageHashCode);
    
}
