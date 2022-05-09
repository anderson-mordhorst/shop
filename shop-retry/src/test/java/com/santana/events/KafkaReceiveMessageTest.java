package com.santana.events;

import com.santana.dto.ShopDTO;
import com.santana.dto.ShopItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class KafkaReceiveMessageTest {

    @InjectMocks
    private KafkaReceiveMessage kafkaReceiveMessage;

    @Mock
    private KafkaTemplate<String, ShopDTO> kafkaTemplate;

    @Mock
    private ShopDTO dto;

    @Test
    @DisplayName("Receber uma compra com sucesso via kafka")
    public void testListenShopTopicSuccess() {
        when(dto.getItems()).thenReturn(singletonList(new ShopItemDTO()));
        kafkaReceiveMessage.listenShopTopic(dto);
        verify(kafkaTemplate, never()).send("SHOP_TOPIC_RETRY", dto);
    }


    @Test
    @DisplayName("Receber uma compra com erro via kafka")
    public void testListenShopTopicError() {
        kafkaReceiveMessage.listenShopTopic(dto);
        verify(kafkaTemplate).send("SHOP_TOPIC_RETRY", dto);
    }
}