package com.santana.events;

import com.santana.dto.ShopDTO;
import com.santana.dto.ShopItemDTO;
import com.santana.model.Product;
import com.santana.repository.ProductRepository;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage {
    
    private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";
    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";

    private final ProductRepository repository;
    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

    @KafkaListener(topics = SHOP_TOPIC_NAME, groupId = "group")
    public void listenShopTopic(@Payload ShopDTO dto,
                                @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp){
        try{
            log.info("Compra recebida no tópico: {} com chave {} na hora {}.", dto.getIdentifier(), key, timestamp);
            boolean success = true;
            for(ShopItemDTO item: dto.getItems()) {
                Product product = repository.findByIdentifier(item.getProductIdentifier());
                if(!isValidShop(item, product)) {
                    shopError(dto);
                    success = false;
                    break;
                }
            }

            if(success) {
                shopSuccess(dto);                    
            }            
        }
        catch(Exception e) {
            log.error("Erro no processamento da compra {}", dto.getIdentifier());
        }
    }

    private void shopSuccess(ShopDTO dto) {
        log.info("Compra efetuada com sucesso {}", dto.getIdentifier());
        dto.setStatus("SUCCESS");
        kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, dto);        
    }

    private void shopError(ShopDTO dto) {
        log.info("Erro no processamento da compra {}", dto.getIdentifier());
        dto.setStatus("ERROR");
        kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, dto);
    }

    private boolean isValidShop(ShopItemDTO item, Product product) {
        return product != null && product.getAmount() >= item.getAmount();
    }
}
