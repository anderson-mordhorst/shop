package com.santana.controller;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.santana.dto.ShopDTO;
import com.santana.events.KafkaClient;
import com.santana.model.Shop;
import com.santana.repository.ShopRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopRepository repository;
    private final KafkaClient kafkaClient;

    @GetMapping
    public List<ShopDTO> getAll() {
        return repository.findAll().stream().map(ShopDTO::convert).collect(toList());
    }

    @PostMapping
    public ShopDTO saveShop(@RequestBody ShopDTO dto) {
        dto.setIdentifier(UUID.randomUUID().toString());
        dto.setDateShop(LocalDate.now());
        dto.setStatus("PENDING");

        Shop entity = Shop.convert(dto);
        entity.getItems().forEach(item -> {
            item.setShop(entity);
        });


        dto = ShopDTO.convert(repository.save(entity));
        kafkaClient.sendMessage(dto);
        return dto;
    }
}
