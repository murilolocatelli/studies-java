package com.study.ecommerce.kafka.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CorrelationIdDto {
    
    private String id;

    public CorrelationIdDto(String name) {
        this.id = name + "(" + UUID.randomUUID().toString() + ")";
    }

    public CorrelationIdDto continueWith(String name) {
        return new CorrelationIdDto(this.id + "-" + name);
    }

}
