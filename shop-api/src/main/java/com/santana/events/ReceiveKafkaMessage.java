package com.santana.events;


import com.santana.dto.ShopDTO;
import com.santana.model.Shop;
import com.santana.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage {

    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";
    private final ShopRepository repository;

    @KafkaListener(topics = SHOP_TOPIC_EVENT_NAME, groupId = "group")
    public void listenShopEvents(ShopDTO dto) {
        try{
            log.info("Status da compra recebida no tópico {}", dto.getIdentifier());
            Shop shop = repository.findByIdentifier(dto.getIdentifier());
            shop.setStatus(dto.getStatus());
            repository.save(shop);
        }
        catch(Exception e) {
            log.error("Erro no processamento da compra {}", dto.getIdentifier());
        }
    }
}
