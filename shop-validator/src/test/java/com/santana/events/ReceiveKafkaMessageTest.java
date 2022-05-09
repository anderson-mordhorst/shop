package com.santana.events;

import com.santana.dto.ShopDTO;
import com.santana.dto.ShopItemDTO;
import com.santana.model.Product;
import com.santana.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReceiveKafkaMessageTest {

    @InjectMocks
    private ReceiveKafkaMessage receiveKafkaMessage;

    @Mock
    private ProductRepository repository;

    @Mock
    private KafkaTemplate<String, ShopDTO> kafkaTemplate;

    private ShopDTO dto;

    private Product product;

    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";

    @BeforeEach
    public void setUp(){
        dto = createShopDTO();
        product = createProdudct();
    }

    @Test
    @DisplayName("Receber mensagem do kafka com sucesso")
    public void testListenShopTopicSucess() {
        when(repository.findByIdentifier(product.getIdentifier())).thenReturn(product);
        receiveKafkaMessage.listenShopTopic(dto, "key", "data");
        verify(kafkaTemplate).send(SHOP_TOPIC_EVENT_NAME, dto);
        Assertions.assertThat(dto.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    @DisplayName("Receber mensagem do kafka com produto não encontrado")
    public void testListenShopTopicProductNotFound() {
        when(repository.findByIdentifier(product.getIdentifier())).thenReturn(null);
        receiveKafkaMessage.listenShopTopic(dto, "key", "data");
        verify(kafkaTemplate).send(SHOP_TOPIC_EVENT_NAME, dto);
        Assertions.assertThat(dto.getStatus()).isEqualTo("ERROR");
    }

    private Product createProdudct() {
        Product product = new Product();
        product.setAmount(1000);
        product.setId(1L);
        product.setIdentifier("product-1");

        return product;
    }

    private ShopDTO createShopDTO(){
        ShopDTO dto = new ShopDTO();
        dto.setBuyerIdentifier("b-1");

        ShopItemDTO itemDTO = new ShopItemDTO();
        itemDTO.setAmount(1000);
        itemDTO.setProductIdentifier("product-1");
        itemDTO.setPrice(100f);

        dto.getItems().add(itemDTO);
        return dto;
    }
}