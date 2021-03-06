package com.santana.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ShopDTO {

    private String identifier;
    private LocalDate dateShop;
    private String status;
    private String buyerIdentifier;
    private List<ShopItemDTO> items = new ArrayList<>();
}
