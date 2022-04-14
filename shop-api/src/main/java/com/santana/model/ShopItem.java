package com.santana.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.santana.dto.ShopItemDTO;

import lombok.Data;

@Data
@Entity(name = "shop_item")
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_identifier")
    private String productIdentifier;

    private Integer amount;

    private Float price;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    

    public static ShopItem convert(ShopItemDTO dto) {
        ShopItem entity = new ShopItem();
        entity.setProductIdentifier(dto.getProductIdentifier());
        entity.setAmount(dto.getAmount());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
