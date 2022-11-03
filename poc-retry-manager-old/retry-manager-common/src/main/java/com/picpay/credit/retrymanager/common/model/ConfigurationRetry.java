package com.picpay.credit.retrymanager.common.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "configuration_retry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ConfigurationRetry {

    @Id
    private String id;

    private String feature;

    private String topic;

    @Field("topic_dlq")
    private String topicDlq;
    
    @Field("key_headers")
    private List<String> keyHeaders;
    
    @Field("max_attempts")
    private Integer maxAttempts;

    @Field("initial_interval_seconds")
    private Integer initialIntervalSeconds;

    @Field("interval_multiplier")
    private Integer intervalMultiplier;

    private Boolean active;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;
    
}
