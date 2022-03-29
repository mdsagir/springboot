package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

@RestController
public class WebRestController {

    private final Sinks.Many<String> sink;

    public WebRestController(Sinks.Many<String> sink) {
        this.sink = sink;
    }

    @GetMapping("/demo/{message}")
    public void post(@PathVariable("message") String message) {
        sink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
