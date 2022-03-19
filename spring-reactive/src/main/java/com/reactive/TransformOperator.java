package com.reactive;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransformOperator {
    public static void main(String[] args) {
        final List<HandleOperator.Department> departments = IntStream.range(1, 100).mapToObj(HandleOperator.Department::new).collect(Collectors.toList());
        Function<Flux<HandleOperator.Department>, Flux<HandleOperator.Department>> applyFunction = departmentFlux -> departmentFlux.map(department -> {
            department.setDepartmentName(UUID.randomUUID().toString().substring(1, 5).toUpperCase());
            return department;
        });
        final Flux<HandleOperator.Department> departmentFlux = Flux.fromIterable(departments).transform(applyFunction);
        departmentFlux.subscribe(System.out::println);
        
    }
}
