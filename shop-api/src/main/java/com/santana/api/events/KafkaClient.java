package com.santana.api.events;

import com.santana.api.dto.ShopDTO;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaClient {
    
    private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";
    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

    public void sendMessage(ShopDTO dto) {
        kafkaTemplate.send(SHOP_TOPIC_NAME, dto);
    }
}
