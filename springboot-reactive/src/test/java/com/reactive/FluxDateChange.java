package com.reactive;

import com.reactive.project.model.LineChartDataResponse;
import reactor.core.publisher.Flux;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class FluxDateChange {

    public static void main(String[] args) {

        AtomicReference<LocalDate> localDate = new AtomicReference<>();

        List<LineChartDataResponse> lineChartDataResponses = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            lineChartDataResponses.add(new LineChartDataResponse(i, LocalDateTime.now().plusDays(i)));
        }

        Flux<LineChartDataResponse> lineChartData = Flux.fromIterable(lineChartDataResponses);

        Predicate<LineChartDataResponse> localDateTimePredicate = localDateTime2 -> {
            LocalDate localDate1 = localDateTime2.getTimeStamp().toLocalDate();

            if (Objects.isNull(localDate.getPlain())) {
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

        Flux<LineChartDataResponse> filter = lineChartData.filter(localDateTimePredicate);
        filter.subscribe(lineChartDataResponse -> {
            System.out.println("Data : " + lineChartDataResponse.getTimeStamp());
        });

    }

    public static boolean isReadyDate(LocalDateTime localDateTime) {


        return true;
    }
}
