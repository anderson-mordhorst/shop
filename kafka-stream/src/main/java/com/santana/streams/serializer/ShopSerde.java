package com.santana.streams.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ShopSerde implements Serde {

    private ShopSerializer serializer = new ShopSerializer();
    private ShopDeserializer deserializer = new ShopDeserializer();

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer serializer() {
        return serializer;
    }

    @Override
    public Deserializer deserializer() {
        return deserializer;
    }
}
