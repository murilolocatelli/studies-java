package com.study.ecommerce.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MessageDto<T> {
    
    private CorrelationIdDto id;
    private T payload;

}
