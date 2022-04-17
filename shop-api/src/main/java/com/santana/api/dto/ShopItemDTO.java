package com.santana.api.dto;

import com.santana.api.model.ShopItem;

import lombok.Data;

@Data
public class ShopItemDTO {

    private String productIdentifier;
    private Integer amount;
    private Float price;

    public static ShopItemDTO convert(ShopItem entity) {
        ShopItemDTO dto = new ShopItemDTO();
        dto.setProductIdentifier(entity.getProductIdentifier());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());

        return dto;
    }
}
