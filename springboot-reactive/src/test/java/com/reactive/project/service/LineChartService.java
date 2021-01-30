package com.reactive.project.service;

import com.reactive.project.model.ChartLineRequest;
import com.reactive.project.model.LineChartDataResponse;
import com.reactive.project.model.StockFilters;
import com.reactive.project.model.TickerValue;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LineChartService {

    public static void main(String[] args) {

        StockFilters stockFilters = new StockFilters();
        stockFilters.setDays("1");
        stockFilters.setTickerId("1001");
        stockFilters.setTickerType("BSE");

        StockFilters stockFilters1 = new StockFilters();
        stockFilters1.setDays("2");
        stockFilters1.setTickerId("1002");
        stockFilters1.setTickerType("NSE");

        ChartLineRequest chartLineRequest = new ChartLineRequest();
        chartLineRequest.setStockFilters(List.of(stockFilters, stockFilters1));


        Flux<TickerValue> tickerValueFlux = Flux.fromStream(chartLineRequest.getStockFilters()
                .stream()
                .filter(stockFilters2 -> stockFilters2.getDays().equals("1"))
                .map(stockFilters2 -> new TickerValue(stockFilters2.getTickerId(), oneDay(stockFilters2.getTickerId(), stockFilters2.getTickerType()))));

        tickerValueFlux.subscribe(System.out::println);

    }

    public static void processingChartLine(ChartLineRequest chartLineRequest) {


    }

    public static List<LineChartDataResponse> oneDay(String ticker, String tickerType) {

        List<LineChartDataResponse> lineChartDataResponses = new ArrayList<>();
        Flux<LineChartDataResponse> just = Flux.just(new LineChartDataResponse(122, LocalDateTime.now()));
        just.doOnNext(lineChartDataResponse -> lineChartDataResponses.add(new LineChartDataResponse(lineChartDataResponse.getClose(), lineChartDataResponse.getTimeStamp()))).subscribe();
        return lineChartDataResponses;
    }

    public static Flux<LineChartDataResponse> fiveDay(String ticker, String tickerType) {
        return Flux.empty();
    }

    public static Flux<LineChartDataResponse> indexChart(String ticker, String tickerType) {
        return Flux.empty();
    }

    public static Flux<LineChartDataResponse> historicalChart(String ticker, String tickerType) {
        return Flux.empty();
    }

}
