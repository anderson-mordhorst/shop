package com.santana.events;

import com.santana.dto.ShopDTO;
import com.santana.repository.ShopReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage {

    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";
    private final ShopReportRepository repository;

    @Transactional
    @KafkaListener(topics = SHOP_TOPIC_EVENT_NAME, groupId = "group_report")
    public void listenShopTopic(ShopDTO dto){
        try{
            log.info("Compra recebida no tópico {}", dto.getIdentifier());
            repository.incrementShopStatus(dto.getStatus());
        }
        catch(Exception e) {
            log.error("Erro no processamento da mensagem", e);
        }
    }
}
