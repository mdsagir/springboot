package com.reactive;

import com.reactive.document.Address;
import com.reactive.document.Product;
import com.reactive.document.Request;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MonoToListTest {

    @Test
    void monoToList() {

        Mono<Integer> mono = Mono.just(1);

        List<String> arrayList = new ArrayList<>();

        mono.subscribe(integer -> {
            List<String> list = stringList(integer);
            arrayList.addAll(list);
        });
        System.out.println(arrayList);
    }

    public List<String> stringList(int i) {
        return List.of("A", "B", "C");
    }

    @Test
    void streamTest() {

        String ticker="I0009";

        Stream.of(ticker)
                .filter(s -> s.startsWith("I"))
                .forEach(s -> System.out.println("Printin Index "+s));

        Stream.of(ticker)
                .filter(s -> !s.startsWith("I"))
                .forEach(s -> System.out.println("Printin Stock "+s));

    }

    @Test
    void mono_to_list() {

        Request request=new Request();
        request.setIntegers(List.of(1,2));

        List<Integer> integers1 = request.getIntegers();
        Flux<Integer> integerFlux = Flux.fromIterable(integers1);

        Flux<Product> productFlux = integerFlux.flatMap(integers -> {

            Product product = new Product();
            product.setId(integers.toString());

            Mono<Product> productMono = Mono.just(product);

            Flux<Address> addressFlux = Flux.just(
                    new Address(1),
                    new Address(2));

            return Flux.zip(productMono, addressFlux.collectList(), (x, y) -> {
                x.setAddress(y);
                return x;
            });
        });
        productFlux.subscribe(product -> System.out.println("PR "+product));
    }
}
