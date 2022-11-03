package com.picpay.credit.retrymanager.api.business;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.picpay.credit.retrymanager.api.dto.PocDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendMessageBusiness {

    @Autowired
    private StreamBridge streamBridge;

    public void sendMessage(String bindingName, PocDto pocDto) throws InterruptedException, ExecutionException {
        Message<PocDto> message = MessageBuilder
            .withPayload(pocDto)
            .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString().getBytes())
            .setHeader("correlationId", UUID.randomUUID().toString().getBytes())
            .setHeader("consumerId", new Random().nextInt(1000000))
            .build();
        
        boolean send = this.streamBridge.send(bindingName.concat("-out-0"), message);

        if (!send) {
            throw new RuntimeException("Error sending message to kafka");
        }

        log.info("Message produced: {}", pocDto);
    }

}
