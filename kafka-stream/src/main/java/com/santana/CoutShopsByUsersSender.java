package com.santana;

import com.santana.config.ConfigFactory;
import com.santana.dto.ShopDTO;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

public class CoutShopsByUsersSender {

    private static final String SHOP_TOPIC = "SHOP_TOPIC";

    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, ShopDTO> inputTopic = builder.stream(SHOP_TOPIC);
        KTable<String, Long> comprasPorUsuario = inputTopic
                .groupByKey()
                .count(Materialized.<String, Long, KeyValueStore<Bytes,byte[]>>as("count-store"));

        comprasPorUsuario.toStream().to("CountUsersByKey", Produced.with(Serdes.String(), Serdes.Long()));
        KafkaStreams streams = new KafkaStreams(builder.build(), ConfigFactory.createDefaultProperties());
        streams.start();
    }
}
