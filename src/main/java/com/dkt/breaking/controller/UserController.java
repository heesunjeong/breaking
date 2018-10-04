package com.dkt.breaking.controller;

import com.dkt.breaking.model.User;
import com.dkt.breaking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    public Mono<Boolean> addUser(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

    @GetMapping("check/{email}")
    public Mono<Boolean> checkDuplication(@PathVariable String email) {
        return userService.getUserByEmail(email)
            .map(result -> result)
            .defaultIfEmpty(false);
    }

    @PostMapping("/login")
    public Mono<User> login(ServerWebExchange exchange, WebSession session) {
        return ReactiveSecurityContextHolder
            .getContext()
            .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .cast(User.class);
    }

    private void addTokenHeader(ServerHttpResponse response, UserDetails userDetails) {

    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}