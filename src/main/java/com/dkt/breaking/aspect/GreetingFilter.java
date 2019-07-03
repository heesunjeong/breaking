package com.dkt.breaking.aspect;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GreetingFilter {

    public HandlerFilterFunction<ServerResponse, ServerResponse> filter() {
        return (request, next) -> {
            log.debug("request: {}, next: {}", request, next);
            return next.handle(request);
        };
    }
}
