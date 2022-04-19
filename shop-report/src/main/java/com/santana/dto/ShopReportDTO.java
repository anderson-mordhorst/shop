package com.santana.dto;

import com.santana.model.ShopReport;
import lombok.Data;

@Data
public class ShopReportDTO {

    private String identifier;
    private Integer amount;

    public static ShopReportDTO convert(ShopReport report) {
        ShopReportDTO dto = new ShopReportDTO();
        dto.setIdentifier(report.getIdentifier());
        dto.setAmount(report.getAmount());
        return dto;
    }
}
