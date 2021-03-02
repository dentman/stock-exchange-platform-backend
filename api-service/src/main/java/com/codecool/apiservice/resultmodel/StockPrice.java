package com.codecool.apiservice.resultmodel;

import com.codecool.apiservice.apimodel.ChartDataPoint;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StockPrice {

    private Long id;

    private String symbol;
    private LocalDate date;
    private BigDecimal price;
    private boolean isClosing = true;

    private Long stockId;

    public StockPrice(ChartDataPoint dataPoint) {
        this.symbol = dataPoint.getSymbol();
        this.date = LocalDate.parse(dataPoint.getDate());
        this.price = BigDecimal.valueOf(dataPoint.getClose());
    }

}
