package com.reactive.service;

import com.reactive.model.ChartLineRequest;
import com.reactive.model.LineChartDataResponse;
import com.reactive.model.StockHistoricalPrice;
import com.reactive.model.TickerValue;
import com.reactive.repo.StockHistoricalPriceRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineChartServiceImpl implements LineChartService {

    private final StockHistoricalPriceRepo stockHistoricalPriceRepo;


    @Override
    public Flux<TickerValue> processLineChartsByIds(ChartLineRequest chartLineRequest) {
        AtomicReference<LocalDate> localDate = new AtomicReference<>();

        Predicate<StockHistoricalPrice> localDateTimePredicate = localDateTime2 -> {
            LocalDate localDate1 = localDateTime2.getTimestamp().toLocalDate();

            if (Objects.isNull(localDate.getPlain())) {
                log.info("Log for first time");
                localDate.set(localDate1.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
                return true;
            } else {

                if (localDate1.isAfter(localDate.get())) {
                    if (localDate1.getDayOfWeek().equals(DayOfWeek.MONDAY)
                            || localDate1.getDayOfWeek().equals(DayOfWeek.TUESDAY)
                            || localDate1.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {

                        localDate.set(localDate1.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
                        return true;
                    }
                }
            }
            return false;
        };

        return Flux.fromIterable(chartLineRequest.getStockFilters()).concatMap(stockFilters -> {

            String tickerId = stockFilters.getTickerId();

            Mono<TickerValue> tickerValueMono = Mono.fromSupplier(() -> {
                TickerValue tickerValue = new TickerValue();
                tickerValue.setTickerId(tickerId);
                return tickerValue;
            });

            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusYears(5);

            Flux<StockHistoricalPrice> stocks = stockHistoricalPriceRepo.findAllByTimestampBetweenAndTickerId(startDate, endDate,
                    stockFilters.getTickerId(), stockFilters.getTickerType());


            Flux<LineChartDataResponse> lineChartDataResponseFlux =
                    stocks.filter(localDateTimePredicate)
                            .flatMap(stockHistoricalPriceV21 -> Mono.just(LineChartDataResponse.builder()
                                    .close(stockHistoricalPriceV21.getClose())
                                    .timeStamp(stockHistoricalPriceV21.getTimestamp()).build()));
            localDate.setPlain(null);
            return Mono.zip(tickerValueMono, lineChartDataResponseFlux.collectList(),
                    (tickerValue, lineChartDataResponse) -> {
                        tickerValue.setValues(lineChartDataResponse);
                        return tickerValue;
                    });
        });
    }
}
