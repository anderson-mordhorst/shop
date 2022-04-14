package com.santana.model;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.santana.dto.ShopDTO;

import lombok.Data;

@Data
@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String identifier;

    private String status;

    @Column(name = "date_shop")
    private LocalDate dateShop;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shop")
    private List<ShopItem> items;

    public static Shop convert(ShopDTO dto) {
        Shop entity = new Shop();
        entity.setIdentifier(dto.getIdentifier());
        entity.setStatus(dto.getStatus());
        entity.setDateShop(dto.getDateShop());
        entity.setItems(dto.getItems().stream().map(ShopItem::convert).collect(toList()));

        return entity;
    }
}
