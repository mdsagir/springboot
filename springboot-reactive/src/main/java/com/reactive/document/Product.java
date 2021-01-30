package com.reactive.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@Data
public class Product {

    private String id;
    private List<Address> address;
}
