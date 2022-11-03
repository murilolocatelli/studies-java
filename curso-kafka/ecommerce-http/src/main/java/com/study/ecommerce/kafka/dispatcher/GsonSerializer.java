package com.study.ecommerce.kafka.dispatcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.ecommerce.kafka.MessageAdapter;
import com.study.ecommerce.kafka.dto.MessageDto;

import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        Gson gson = new GsonBuilder().registerTypeAdapter(MessageDto.class, new MessageAdapter()).create();

        return gson.toJson(data).getBytes();
    }

}
