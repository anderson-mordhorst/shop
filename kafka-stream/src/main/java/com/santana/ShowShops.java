package com.santana;

import com.santana.config.ConfigFactory;
import com.santana.dto.ShopDTO;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

public class ShowShops {

    private static final String SHOP_TOPIC = "SHOP_TOPIC";

    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream <String, ShopDTO> inputTopic = builder.stream(SHOP_TOPIC);
        inputTopic.print(Printed.<String, ShopDTO>toSysOut());
        KafkaStreams streams = new KafkaStreams(builder.build(), ConfigFactory.createDefaultProperties());
        streams.start();
    }
}
