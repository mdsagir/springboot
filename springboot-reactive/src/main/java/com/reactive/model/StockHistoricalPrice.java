package com.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("stock_historical_price")
public class StockHistoricalPrice {

    @Id
    private int id;

    private LocalDateTime timestamp;

    private double open;

    private double high;

    private double low;

    private double close;

    private long volume;

    private double ask;

    private double vwap;

    private double bid;

    private String tickerId;

    private String tickerType;

    private String commonName;

    private String ric;

}
