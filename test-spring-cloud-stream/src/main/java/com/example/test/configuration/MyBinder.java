package com.example.test.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyBinder {

    String ORDER_IN = "order-in";
    String ORDER_OUT = "order-out";

    @Input(ORDER_IN)
    SubscribableChannel orderIn();

    @Output(ORDER_OUT)
    MessageChannel orderOut();

}
