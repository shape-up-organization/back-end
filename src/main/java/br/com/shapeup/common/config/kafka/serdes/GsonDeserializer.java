package br.com.shapeup.common.config.kafka.serdes;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer<T> implements Deserializer<T> {
    private Gson gson = new Gson();
    private Class<T> deserializedClass;

    public GsonDeserializer(Class<T> deserializedClass) {
        this.deserializedClass = deserializedClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        String dataAsString = new String(data);
        return gson.fromJson(dataAsString, deserializedClass);
    }
}
