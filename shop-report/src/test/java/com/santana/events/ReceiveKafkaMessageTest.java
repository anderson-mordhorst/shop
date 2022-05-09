package com.santana.events;

import com.santana.dto.ShopDTO;
import com.santana.repository.ShopReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ReceiveKafkaMessageTest {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private ShopReportRepository repository;

    private ShopDTO dto;

    @BeforeEach
    public void setUp(){
        dto = new ShopDTO();
        dto.setStatus("SUCCESS");
    }

    @Test
    @DisplayName("Incrementar quantidade de compras por status")
    public void testListenShopTopic() {
        receiveKafkaMessage.listenShopTopic(dto);
        verify(repository).incrementShopStatus(dto.getStatus());
    }
}