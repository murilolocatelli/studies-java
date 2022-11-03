package com.picpay.credit.retrymanager.common.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "message_retry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class MessageRetry {

    @Id
    private String id;

    private Integer attempt;
    
    private String feature;

    private String topic;
    
    private String payload;

    private Map<String, String> headers;

    private String exceptionMessage;
    
    private MessageRetryStatus status;

    private Integer messageHashCode;
    
    @Field("execution_time")
    private LocalDateTime executionTime;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

}
