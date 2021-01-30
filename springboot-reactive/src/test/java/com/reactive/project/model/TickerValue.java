package com.reactive.project.model;

import java.util.List;

public class TickerValue {
    private String tickerId;
    private List<LineChartDataResponse> values;


    public TickerValue() {

    }

    public TickerValue(String tickerId, List<LineChartDataResponse> values) {
        this.tickerId = tickerId;
        this.values = values;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public List<LineChartDataResponse> getValues() {
        return values;
    }

    public void setValues(List<LineChartDataResponse> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "TickerValue{" +
                "tickerId='" + tickerId + '\'' +
                ", values=" + values +
                '}';
    }
}
