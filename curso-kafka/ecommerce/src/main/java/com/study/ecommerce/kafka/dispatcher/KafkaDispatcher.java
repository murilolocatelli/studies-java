package com.study.ecommerce.kafka.dispatcher;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.study.ecommerce.kafka.dto.CorrelationIdDto;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {

    private KafkaProducer<String, MessageDto<T>> kafkaProducer;

    public KafkaDispatcher() {
        kafkaProducer = new KafkaProducer<>(this.getProperties());
    }

    public void send(String topic, String key, CorrelationIdDto id, T payload) throws InterruptedException, ExecutionException {
        Future<RecordMetadata> sendFuture = sendAsync(topic, key, id, payload);
        sendFuture.get();
    }

    public Future<RecordMetadata> sendAsync(String topic, String key, CorrelationIdDto id, T payload) {
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
            }

            System.out.println("Sucesso. Topic: " + data.topic() + " | Partition: " + data.partition() + " | Offset: "
                    + data.offset() + " | Time: " + data.timestamp());
        };

        var message = new MessageDto<>(id.continueWith("_" + topic), payload);

        var record = new ProducerRecord<String, MessageDto<T>>(topic, key, message);

        return kafkaProducer.send(record, callback);
    }

    public Properties getProperties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }

    @Override
    public void close() {
        this.kafkaProducer.close();
    }

}
