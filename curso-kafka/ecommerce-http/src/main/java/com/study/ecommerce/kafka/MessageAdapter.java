package com.study.ecommerce.kafka;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.study.ecommerce.kafka.dto.MessageDto;

@SuppressWarnings("rawtypes")
public class MessageAdapter implements JsonSerializer<MessageDto> {

    @Override
    public JsonElement serialize(MessageDto messageDto, Type typeOfSrc, JsonSerializationContext context) {
        var jsonObject = new JsonObject();
        jsonObject.addProperty("type", messageDto.getPayload().getClass().getName());
        jsonObject.add("payload", context.serialize(messageDto.getPayload()));
        jsonObject.add("correlationId", context.serialize(messageDto.getId()));
        return jsonObject;
    }

}
