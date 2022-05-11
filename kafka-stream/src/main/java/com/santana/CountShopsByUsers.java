package com.santana;

import com.santana.config.ConfigFactory;
import com.santana.dto.ShopDTO;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;

public class CountShopsByUsers {

    private static final String SHOP_TOPIC = "SHOP_TOPIC";

    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, ShopDTO> inputTopic = builder.stream(SHOP_TOPIC);
        KTable<String, Long> comprasPorUsuario = inputTopic
                .groupByKey()
                .count(Materialized.<String, Long, KeyValueStore<Bytes,byte[]>>as("count-store"));

        comprasPorUsuario.toStream().print(Printed.<String, Long>toSysOut());
        KafkaStreams streams = new KafkaStreams(builder.build(), ConfigFactory.createDefaultProperties());
        streams.start();
    }
}
