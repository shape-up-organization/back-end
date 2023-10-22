package br.com.shapeup.common.config.kafka.serdes;

import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;

public enum GsonBuilderSingletonEnum {
    INSTANCE;

    private final GsonBuilder gsonBuilder;

    GsonBuilderSingletonEnum() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    }

    public GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }
}
