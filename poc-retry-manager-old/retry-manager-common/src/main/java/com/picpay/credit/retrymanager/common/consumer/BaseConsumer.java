package com.picpay.credit.retrymanager.common.consumer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.picpay.credit.retrymanager.common.business.ConfigurationRetryBusiness;
import com.picpay.credit.retrymanager.common.model.ConfigurationRetry;
import com.picpay.credit.retrymanager.common.model.MessageRetry;
import com.picpay.credit.retrymanager.common.model.MessageRetryStatus;
import com.picpay.credit.retrymanager.common.repository.MessageRetryRepository;
import com.picpay.credit.retrymanager.common.util.Constants;
import com.picpay.credit.retrymanager.common.util.KafkaUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

public abstract class BaseConsumer {
    
    @Autowired
    private MessageRetryRepository messageRetryRepository;
    
    @Autowired
    private ConfigurationRetryBusiness configurationRetryBusiness;
    
    protected void consume(Message<String> message, String topicName) {
        
        var configurationRetry = this.configurationRetryBusiness
            .findByTopicDlq(topicName).orElseThrow(() -> new NullPointerException("Retry configuration not found"));
        
        var messageRetry = this.configureMessageRetry(configurationRetry, message);

        this.messageRetryRepository.save(messageRetry);
    }

    protected String getTopicName(Message<String> message) {
        String receivedTopicName = KafkaUtils.getMessageHeader(message, KafkaHeaders.RECEIVED_TOPIC);
        Objects.requireNonNull(receivedTopicName, "Topic name must not be null");

        return receivedTopicName;
    }

    private MessageRetry configureMessageRetry(ConfigurationRetry configurationRetry, Message<String> message) {
        String exceptionMessage = KafkaUtils.getMessageHeader(message, Constants.HEADER_EXCEPTION_MESSAGE);
        Map<String, String> headers = this.buildHeaders(configurationRetry, message);
        
        Integer attempt =
            Optional.ofNullable(KafkaUtils.getMessageHeader(message, Constants.HEADER_ATTEMPT_KEY)).map(Integer::valueOf).orElse(1);

        MessageRetryStatus status;
        LocalDateTime executionTime = null;

        if (attempt >= configurationRetry.getMaxAttempts()) {
            status = MessageRetryStatus.FAILED;
        
        } else {
            status = MessageRetryStatus.TO_REPROCESS;

            Integer seconds;
            if (attempt == 1) {
                seconds = configurationRetry.getInitialIntervalSeconds();
            } else {
                seconds = configurationRetry.getInitialIntervalSeconds() * (attempt - 1) * configurationRetry.getIntervalMultiplier();
            }

            executionTime = LocalDateTime.now().plusSeconds(seconds);
        }

        return MessageRetry.builder()
            .attempt(attempt)
            .feature(configurationRetry.getFeature())
            .topic(configurationRetry.getTopic())
            .payload(message.getPayload())
            .headers(headers)
            .exceptionMessage(exceptionMessage)
            .status(status)
            .executionTime(executionTime)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    private Map<String, String> buildHeaders(ConfigurationRetry configurationRetry, Message<String> message) {
        Map<String, String> headers = new HashMap<>();

        configurationRetry.getKeyHeaders().forEach(key -> {
            headers.put(key, KafkaUtils.getMessageHeader(message, key));
        });

        return headers;
    }
    
}
