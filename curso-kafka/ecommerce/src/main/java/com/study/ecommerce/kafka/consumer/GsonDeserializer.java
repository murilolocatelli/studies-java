package com.study.ecommerce.kafka.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.ecommerce.kafka.MessageAdapter;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.common.serialization.Deserializer;

@SuppressWarnings("rawtypes")
public class GsonDeserializer implements Deserializer<MessageDto> {

    /*public static final String TYPE_CONFIG = "TYPE_DESERIALIZER";

    private Class<T> type;

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));

        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type for deserialization does not exists in the classpath", e);
        }
    }*/

    @Override
    public MessageDto deserialize(String topic, byte[] data) {
        Gson gson = new GsonBuilder().registerTypeAdapter(MessageDto.class, new MessageAdapter()).create();

        return gson.fromJson(new String(data), MessageDto.class);
    }

}
