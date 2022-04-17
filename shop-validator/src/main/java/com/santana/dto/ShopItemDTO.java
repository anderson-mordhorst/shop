package com.santana.dto;

import lombok.Data;

@Data
public class ShopItemDTO {

    private String productIdentifier;
    private Integer amount;
    private Float price;
}
