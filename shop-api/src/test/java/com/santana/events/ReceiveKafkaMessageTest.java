package com.santana.events;

import com.santana.dto.ShopDTO;
import com.santana.dto.ShopItemDTO;
import com.santana.model.Shop;
import com.santana.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReceiveKafkaMessageTest {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private ShopRepository repository;

    @Test
    public void testListenShopEvents() {
        ShopDTO dto = new ShopDTO();
        dto.setStatus("SUCCESS");
        dto.setIdentifier("teste");

        ShopItemDTO itemDTO = new ShopItemDTO();
        itemDTO.setProductIdentifier("produto-1");
        itemDTO.setAmount(100);
        itemDTO.setPrice(20.0f);
        dto.getItems().add(itemDTO);
        Shop shop = Shop.convert(dto);

        when(repository.findByIdentifier(dto.getIdentifier())).thenReturn(shop);
        receiveKafkaMessage.listenShopEvents(dto);

        verify(repository).findByIdentifier(dto.getIdentifier());
        verify(repository).save(shop);
    }
}