package com.reactive.project.model;

import java.time.LocalDateTime;

public class LineChartDataResponse {
    private double close;
    private LocalDateTime timeStamp;

    public LineChartDataResponse() {
    }

    public LineChartDataResponse(double close, LocalDateTime timeStamp) {
        this.close = close;
        this.timeStamp = timeStamp;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "LineChartDataResponse{" +
                "close=" + close +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
