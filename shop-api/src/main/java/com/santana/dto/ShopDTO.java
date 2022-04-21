package com.santana.dto;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.santana.model.Shop;

import lombok.Data;

@Data
public class ShopDTO {

    private String identifier;
    private LocalDate dateShop;
    private String status;
    private String buyerIdentifier;
    private List<ShopItemDTO> items = new ArrayList<>();

    public static ShopDTO convert(Shop entity) {
        ShopDTO dto = new ShopDTO();
        dto.setIdentifier(entity.getIdentifier());
        dto.setBuyerIdentifier(entity.getBuyerIdentifier());
        dto.setStatus(entity.getStatus());
        dto.setDateShop(entity.getDateShop());
        dto.setItems(entity.getItems().stream().map(ShopItemDTO::convert).collect(toList()));

        return dto;
    }
}
