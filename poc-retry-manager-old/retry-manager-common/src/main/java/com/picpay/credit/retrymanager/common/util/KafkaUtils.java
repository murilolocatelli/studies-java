package com.picpay.credit.retrymanager.common.util;

import java.util.Optional;

import org.springframework.messaging.Message;

public class KafkaUtils {

    public static String getMessageHeader(Message<String> message, String key) {
        return Optional.ofNullable(message)
            .map(Message::getHeaders)
            .map(t -> t.get(key))
            .map(t -> {
                if (t instanceof byte[]) {
                    return new String((byte[]) t);
                } else {
                    return t.toString();
                }
            })
            .orElse(null);
    }
    
}
