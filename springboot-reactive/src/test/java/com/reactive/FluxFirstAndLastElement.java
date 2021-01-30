package com.reactive;

import com.reactive.project.model.LineChartDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FluxFirstAndLastElement {

    @Test
    void firstAndLastElement() {

        Flux<String> just = Flux.just("A", "B", "V");
        just.count().subscribe(aLong -> System.out.println("COUNT : " + aLong));
        Mono<String> take = just.take(1).next();
        System.out.println(take.block());
        Mono<String> reduce = just.reduce((s, s2) -> s2);
        System.out.println(reduce.block());

    }

    @Test
    void lineChart() {

        LocalDateTime now = LocalDateTime.now();


        List<LineChartDataResponse> lineChartDataResponses1 = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            lineChartDataResponses1.add(new LineChartDataResponse(i, now.plusDays(i)));
        }

        Flux<LineChartDataResponse> lineChartDataResponseFlux = Flux.fromIterable(lineChartDataResponses1);


        List<LineChartDataResponse> lineChartDataResponseFlux2 = update(lineChartDataResponseFlux);

        lineChartDataResponseFlux2.forEach(lineChartDataResponse -> {
            System.out.println("Data : "+lineChartDataResponse);
        });



    }

    private List<LineChartDataResponse> update(Flux<LineChartDataResponse> lineChartDataResponseFlux) {


        Flux<LineChartDataResponse> lineChartDataResponseFlux2 = lineChartDataResponseFlux.collectList().flatMapMany(lineChartDataResponses -> {

            if (lineChartDataResponses.size() == BigInteger.ZERO.intValue()) {
                return Flux.empty();
            }

            LineChartDataResponse endDate = lineChartDataResponses.get(lineChartDataResponses.size() - 1);
            LineChartDataResponse startDate = lineChartDataResponses.get(0);

            List<LocalDateTime> timeList = new ArrayList<>();

            int i = 0;
            while (startDate.getTimeStamp().isBefore(endDate.getTimeStamp())) {
                if (i >= lineChartDataResponses.size()) {
                    break;
                }
                LocalDateTime localDateTime = lineChartDataResponses.get(i).getTimeStamp();
                LocalDateTime uptoSunday = localDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                List<LocalDateTime> dates = Stream.iterate(localDateTime, date -> date.plusDays(1))
                        .limit(ChronoUnit.DAYS.between(localDateTime, uptoSunday))
                        .collect(Collectors.toList());

                List<LineChartDataResponse> contains = lineChartDataResponses.stream()
                        .filter(lineChartDataResponse -> dates.contains(lineChartDataResponse.getTimeStamp())).collect(Collectors.toList());

                timeList.add(contains.get(0).getTimeStamp());
                int size = contains.size();
                startDate.setTimeStamp(startDate.getTimeStamp().plusDays(size));
                i = i + size;
            }

            return Flux.fromIterable(timeList.stream().flatMap(localDateTime -> lineChartDataResponses.stream()
                    .filter(lineChartDataResponse -> lineChartDataResponse.getTimeStamp().isEqual(localDateTime)))
                    .collect(Collectors.toList()));

        });

        List<LineChartDataResponse> lineChartDataResponseList=new ArrayList<>();

        lineChartDataResponseFlux2.doOnNext(lineChartDataResponseList::add).subscribe();

        return lineChartDataResponseList;
    }
}
