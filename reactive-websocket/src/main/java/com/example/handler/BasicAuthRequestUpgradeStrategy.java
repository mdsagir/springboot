package com.example.handler;

import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.adapter.NettyWebSocketSessionSupport;
import org.springframework.web.reactive.socket.adapter.ReactorNettyWebSocketSession;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

import java.util.function.Supplier;

@Component
public class BasicAuthRequestUpgradeStrategy implements RequestUpgradeStrategy {

    private final int maxFramePayloadLength = NettyWebSocketSessionSupport.DEFAULT_FRAME_MAX_SIZE;

    /*private final AuthenticationService service;

    public BasicAuthRequestUpgradeStrategy(AuthenticationService service) {
        this.service = service;
    }*/

    @Override
    public Mono<Void> upgrade(ServerWebExchange exchange, //
                              WebSocketHandler handler, //
                              String subProtocol, //
                              Supplier<HandshakeInfo> handshakeInfoFactory) {

        System.out.println("Heelo");
        ServerHttpResponse response = exchange.getResponse();
        HttpServerResponse reactorResponse = getNativeResponse(response);

        HandshakeInfo handshakeInfo = handshakeInfoFactory.get();
        NettyDataBufferFactory bufferFactory = (NettyDataBufferFactory) response.bufferFactory();

        String originHeader = handshakeInfo.getHeaders()
                .getOrigin();// you will get ws://user:pass@localhost:8080

        return authenticate(originHeader)//returns Mono<Boolean>
                .filter(Boolean::booleanValue)// filter the result
                .doOnNext(a -> System.out.println("AUTHORIZED"))
                .flatMap(a -> {
                    return reactorResponse.sendWebsocket((in, out) -> {
                        ReactorNettyWebSocketSession session = //
                                new ReactorNettyWebSocketSession(in, out, handshakeInfo, bufferFactory, this.maxFramePayloadLength);
                        return handler.handle(session);
                    });
                })
                .switchIfEmpty(Mono.just("UNATHORIZED")
                        .doOnNext(System.out::println)
                        .then());

    }

    private static HttpServerResponse getNativeResponse(ServerHttpResponse response) {
        if (response instanceof AbstractServerHttpResponse) {
            return ((AbstractServerHttpResponse) response).getNativeResponse();
        } else if (response instanceof ServerHttpResponseDecorator) {
            return getNativeResponse(((ServerHttpResponseDecorator) response).getDelegate());
        } else {
            throw new IllegalArgumentException("Couldn't find native response in " + response.getClass()
                    .getName());
        }
    }

    public Mono<Boolean> authenticate(String header) {
        System.out.println("authenticate" + header);
        return Mono.just(true);
    }
}
