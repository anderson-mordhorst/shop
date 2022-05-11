package com.santana.streams.serializer;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;

public class ShopSerializer implements Serializer {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static Gson gson = new Gson();

    public byte[] serialize(String s, Object o) {
        String json = gson.toJson(o);
        return json.getBytes(CHARSET);
    }

    @Override
    public void close() {
    }
}
