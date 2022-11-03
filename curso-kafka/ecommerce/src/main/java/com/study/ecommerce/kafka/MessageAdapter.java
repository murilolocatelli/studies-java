package com.study.ecommerce.kafka;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.study.ecommerce.kafka.dto.CorrelationIdDto;
import com.study.ecommerce.kafka.dto.MessageDto;

@SuppressWarnings("rawtypes")
public class MessageAdapter implements JsonSerializer<MessageDto>, JsonDeserializer<MessageDto> {

    @Override
    public JsonElement serialize(MessageDto messageDto, Type typeOfSrc, JsonSerializationContext context) {
        var jsonObject = new JsonObject();
        jsonObject.addProperty("type", messageDto.getPayload().getClass().getName());
        jsonObject.add("payload", context.serialize(messageDto.getPayload()));
        jsonObject.add("correlationId", context.serialize(messageDto.getId()));
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MessageDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        var obj = json.getAsJsonObject();
        var payloadType = obj.get("type").getAsString();
        var correlationId = (CorrelationIdDto) context.deserialize(obj.get("correlationId"), CorrelationIdDto.class);
        try {
            var payload = context.deserialize(obj.get("payload"), Class.forName(payloadType));
            return new MessageDto(correlationId, payload);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e);
        }
    }


}
