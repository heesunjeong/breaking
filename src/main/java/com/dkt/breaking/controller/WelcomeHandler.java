package com.dkt.breaking.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class WelcomeHandler {
    public Mono<ServerResponse> sayHello(ServerRequest request) {
        long boardId = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().body(Mono.just("hello"), String.class);
    }

    public Mono<ServerResponse> sayHi(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just("hi"), String.class);
    }
}
