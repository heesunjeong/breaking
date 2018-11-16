package com.dkt.breaking.controller;

import com.dkt.breaking.model.AuthResponse;
import com.dkt.breaking.model.Passwords;
import com.dkt.breaking.model.User;
import com.dkt.breaking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Mono<AuthResponse> login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("register")
    public Mono<Boolean> addUser(@RequestBody @Valid User user) {
        return userService.addUser(user);
    }

    @GetMapping("info/{userId}")
    public Mono<User> getUserInfo(@PathVariable String userId, ServerWebExchange exchange) {
        return userService.getUserById(userId, exchange);
    }

    @PutMapping("update")
    public Mono<Boolean> updateUser(@RequestBody @Valid User user, ServerWebExchange exchange) {
        return userService.updateUser(user, exchange);
    }

    @PutMapping("password")
    public Mono<Boolean> updatePassword(@RequestBody Passwords passwords, ServerWebExchange exchange) {
        return userService.updatePassword(passwords, exchange);
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}