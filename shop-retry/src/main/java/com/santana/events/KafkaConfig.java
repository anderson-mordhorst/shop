package com.santana.events;

import com.santana.dto.ShopDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka.bootstrapAddress:localhost:9092}")
    private String bootstrapAddress;

    @Bean
    public KafkaTemplate<String, ShopDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }    

    private ProducerFactory<String, ShopDTO> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "shop-api");

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShopDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ShopDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    private ConsumerFactory<String, ShopDTO> consumerFactory() {
        JsonDeserializer<ShopDTO> deserializer = new JsonDeserializer<>();
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }
}
