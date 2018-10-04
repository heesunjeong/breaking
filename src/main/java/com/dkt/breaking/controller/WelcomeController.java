package com.dkt.breaking.controller;

import com.dkt.breaking.configuration.security.BreakingUserDetails;
import com.dkt.breaking.model.HelloWorld;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

@RestController
public class WelcomeController {

    @GetMapping(value = "/")
    public Mono<HelloWorld> sayHello(WebSession webSession) {
        HelloWorld hello = new HelloWorld();

        System.out.println(webSession.getId());

        hello.setTitle("hello");
        hello.setMessage("hi");

        return Mono.just(hello);
    }

    @PostMapping("/login")
    public Mono<BreakingUserDetails> login(ServerWebExchange exchange, WebSession session) {

        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .cast(BreakingUserDetails.class)
            .doOnNext(userDetails -> {
                addTokenHeader(exchange.getResponse(), userDetails); // your job to code it the way you want
            });
    }

    private void addTokenHeader(ServerHttpResponse response, UserDetails userDetails) {

    }
}
