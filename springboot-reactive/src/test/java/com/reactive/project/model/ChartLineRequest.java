package com.reactive.project.model;

import java.util.List;

public class ChartLineRequest {
    private List<StockFilters> stockFilters;

    public List<StockFilters> getStockFilters() {
        return stockFilters;
    }

    public void setStockFilters(List<StockFilters> stockFilters) {
        this.stockFilters = stockFilters;
    }
}
