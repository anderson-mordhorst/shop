package com.santana.events;

import com.santana.dto.ShopDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class KafkaClientTest {

    @InjectMocks
    private KafkaClient kafkaClient;

    @Mock
    private KafkaTemplate<String, ShopDTO> kafkaTemplate;

    private static final String TOPIC_NAME = "SHOP_TOPIC";

    @Test
    public void testSendMessage(){
        ShopDTO dto = new ShopDTO();
        dto.setStatus("SUCCESS");
        dto.setBuyerIdentifier("B1");

        kafkaClient.sendMessage(dto);

        verify(kafkaTemplate).send(TOPIC_NAME, dto.getBuyerIdentifier(), dto);
    }
}