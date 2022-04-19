package com.santana.controller;

import com.santana.dto.ShopReportDTO;
import com.santana.repository.ShopReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ShopReportController {

    private final ShopReportRepository repository;

    @GetMapping
    public List<ShopReportDTO> getShopReport(){
        return repository.findAll().stream().map(ShopReportDTO::convert).collect(toList());
    }
}
