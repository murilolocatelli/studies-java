package com.study.ecommerce.kafka.consumer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.study.ecommerce.kafka.dispatcher.GsonSerializer;
import com.study.ecommerce.kafka.dispatcher.KafkaDispatcher;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaService<T> implements Closeable {

    private KafkaConsumer<String, MessageDto<T>> kafkaConsumer;
    private Consumer<ConsumerRecord<String, MessageDto<T>>> parse;
    
    public KafkaService(String simpleName, Consumer<ConsumerRecord<String, MessageDto<T>>> parse, Map<String, String> overrideProperties) {
        this.kafkaConsumer = new KafkaConsumer<>(this.getProperties(simpleName, overrideProperties));
        this.parse = parse;
    }

    public KafkaService(String topic, String simpleName, Consumer<ConsumerRecord<String, MessageDto<T>>> parse) {
        this(simpleName, parse, null);
        this.kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    public KafkaService(Pattern topic, String simpleName, Consumer<ConsumerRecord<String, MessageDto<T>>> parse, Map<String, String> overrideProperties) {

        this(simpleName, parse, overrideProperties);
        this.kafkaConsumer.subscribe(topic);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void run() {
        while (true) {
            var records = this.kafkaConsumer.poll(Duration.ofMillis(100));

            records.forEach(record -> {
                try {
                    parse.accept(record);
                } catch (Exception e) {
                    try (var deadLetter = new KafkaDispatcher<>();
                            var gsonSerializer = new GsonSerializer()) {
                        
                        deadLetter.send(
                            "ECOMMERCE_DEADLETTER", record.key(), record.value().getId().continueWith("DeadLetter"),
                            gsonSerializer.serialize("", record.value()));
                    } catch (InterruptedException | ExecutionException e1) {
                        e1.printStackTrace();
                    }
                }
                sleep(50);
            });
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties(String simpleName, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, simpleName);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, simpleName + UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        Optional.ofNullable(overrideProperties)
            .ifPresent(t -> properties.putAll(t));

        return properties;
    }

    @Override
    public void close() {
        this.kafkaConsumer.close();
    }

}
