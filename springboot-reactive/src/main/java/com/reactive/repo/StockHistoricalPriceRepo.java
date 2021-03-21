package com.reactive.repo;

import com.reactive.model.StockHistoricalPrice;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface StockHistoricalPriceRepo extends R2dbcRepository<StockHistoricalPrice, Integer> {

    @Query("select * from stock_historical_price where timestamp  >= :startDate and timestamp < :endDateTime and ticker_id =:tickerId and ticker_type =:tickerType order by timestamp")
    Flux<StockHistoricalPrice> findAllByTimestampBetweenAndTickerId(LocalDate startDate, LocalDate endDateTime, String tickerId, String tickerType);
}
