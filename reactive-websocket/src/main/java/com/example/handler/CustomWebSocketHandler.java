package com.example.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {


    private final Sinks.Many<String> sink;

    public CustomWebSocketHandler(Sinks.Many<String> sink) {
        this.sink = sink;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        final Flux<WebSocketMessage> messageFlux = sink.asFlux().map(session::textMessage);
        /*final Flux<WebSocketMessage> messageFlux = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(message -> new StringBuilder(message).reverse())
                .map(message -> session.textMessage(message.toString()));*/
        return session.send(messageFlux);
    }


}
