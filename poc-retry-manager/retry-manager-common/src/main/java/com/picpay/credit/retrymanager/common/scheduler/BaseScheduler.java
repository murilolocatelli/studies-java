package com.picpay.credit.retrymanager.common.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.picpay.credit.retrymanager.common.model.MessageRetry;
import com.picpay.credit.retrymanager.common.model.MessageRetryStatus;
import com.picpay.credit.retrymanager.common.repository.MessageRetryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseScheduler {
    
    @Autowired
    private MessageRetryRepository messageRetryRepository;

    @Autowired
    private StreamBridge streamBridge;
    
    protected void process(List<MessageRetry> messagesRetry) {
        
        messagesRetry.forEach(messageRetry -> {
            log.info("Processing message: {}", messageRetry.getPayload());
            
            messageRetry.setStatus(MessageRetryStatus.REPROCESSED);
            
            Message<String> message =
                MessageBuilder
                .withPayload(messageRetry.getPayload())
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString().getBytes())
                .copyHeaders(messageRetry.getHeaders())
                .build();

            this.sendMessage(messageRetry.getTopic(), message);

            this.messageRetryRepository.save(messageRetry);
        });
    }

    protected List<MessageRetry> findMessagesRetry(String feature) {
        return this.messageRetryRepository.findMessagesRetry(
            feature, MessageRetryStatus.TO_REPROCESS, LocalDateTime.now());
    }

    protected void sendMessage(String topic, Message<String> message) {
        boolean send = this.streamBridge.send(this.mapTopicBinding().get(topic), message);
        
        if (!send) {
            throw new RuntimeException("Error sending message to kafka");
        }

        log.info("Message produced: {}", message.getPayload());
    }

    protected abstract Map<String, String> mapTopicBinding();

}
