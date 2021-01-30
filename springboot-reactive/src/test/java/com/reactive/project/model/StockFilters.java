package com.reactive.project.model;

public class StockFilters {

    private String tickerType;
    private String days;
    private String tickerId;

    public String getTickerType() {
        return tickerType;
    }

    public void setTickerType(String tickerType) {
        this.tickerType = tickerType;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }
}
