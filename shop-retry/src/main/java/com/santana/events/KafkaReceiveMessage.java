package com.santana.events;

import com.santana.dto.ShopDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaReceiveMessage {

    private static final String SHOP_TOPIC = "SHOP_TOPIC";

    private static final String SHOP_TOPIC_RETRY = "SHOP_TOPIC_RETRY";

    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

    @KafkaListener(topics = SHOP_TOPIC, groupId = "group_retry")
    public void listenShopTopic(ShopDTO dto) {
        try{
            log.info("Compra recebida no tópico: {}", dto.getIdentifier());
            if(dto.getItems() == null || dto.getItems().isEmpty()){
                log.error("Compra sem itens");
                throw new Exception();
            }
        }  catch(Exception e) {
            log.info("Erro na aplicacão");
            kafkaTemplate.send(SHOP_TOPIC_RETRY, dto);
        }
    }

    @KafkaListener(topics = SHOP_TOPIC_RETRY, groupId = "group_retry")
    public void listenShopTopicRetry(ShopDTO dto) {
        log.info("Retentativa de processamento {}", dto.getIdentifier());
    }
}
