package com.reactive.service;

import com.reactive.model.ChartLineRequest;
import com.reactive.model.TickerValue;
import reactor.core.publisher.Flux;

public interface LineChartService {

    Flux<TickerValue> processLineChartsByIds(ChartLineRequest chartLineRequest);
}
