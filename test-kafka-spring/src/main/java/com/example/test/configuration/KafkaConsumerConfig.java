package com.example.test.configuration;

import java.util.HashMap;
import java.util.Map;

import com.example.test.dto.StudentDto;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private String bootstrapAddress = "localhost:29092";
    // private String groupId = "myGroupId";

    @Bean
    public ConsumerFactory<String, StudentDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        // props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(StudentDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StudentDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StudentDto> filterKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(record -> record.value().getName().contains("Murilo10"));

        return factory;
    }

    @KafkaListener(topics = "myTopic", groupId = "myGroupId", containerFactory = "filterKafkaListenerContainerFactory")
    // @KafkaListener(groupId = "myGroupId", topicPartitions = @TopicPartition(topic = "myTopic", partitions = {"0"})
    // )
    // @KafkaListener(
    //     groupId = "myGroupId",
    //     containerFactory = "filterKafkaListenerContainerFactory",
    //     topicPartitions =
    //         @TopicPartition(topic = "myTopic", partitionOffsets = {
    //             @PartitionOffset(partition = "0", initialOffset = "0"),
    //             @PartitionOffset(partition = "1", initialOffset = "0"),
    //             @PartitionOffset(partition = "2", initialOffset = "0"),
    //             @PartitionOffset(partition = "3", initialOffset = "0")
    //         })
    // )
    public void listenWithHeaders(
        @Payload StudentDto message,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
        @Header(KafkaHeaders.OFFSET) int offset) {

        System.out.println("Received Message: " + message + " - partition: " + partition + " - offset: " + offset);
    }

}
