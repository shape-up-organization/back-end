package br.com.shapeup.common.config.kafka.serdes;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serializer;

@RequiredArgsConstructor
public class GsonSerializer<T> implements Serializer<T> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, T data) {
        return gson.toJson(data).getBytes();
    }
}
