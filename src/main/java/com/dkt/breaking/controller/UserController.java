package com.dkt.breaking.controller;

import com.dkt.breaking.configuration.security.BreakingUserDetailService;
import com.dkt.breaking.configuration.security.BreakingUserDetails;
import com.dkt.breaking.configuration.security.JwtTokenProvider;
import com.dkt.breaking.configuration.security.UserRole;
import com.dkt.breaking.model.AuthResponse;
import com.dkt.breaking.model.User;
import com.dkt.breaking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;

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

    @Autowired
    private BreakingUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public Mono<ResponseEntity> login(@RequestBody User reqUser) {
        return userDetailService.findByUsername(reqUser.getEmail())
            .map(userDetails -> {
                if (passwordEncoder.matches(reqUser.getPassword(), userDetails.getPassword())) {
                    return ResponseEntity.ok(new AuthResponse(jwtTokenProvider.generateToken(userDetails), ((BreakingUserDetails) userDetails).getUser().getName(), userDetails.getUsername()));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            });
    }

    @PostMapping("register")
    public Mono<Boolean> addUser(@RequestBody @Valid User user) {
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));
        return userService.saveUser(user);
    }

    @GetMapping("info")
    public Mono<User> getUserInfo(ServerWebExchange exchange) {
        String email = userService.getUserNameByToken(exchange);
        return userService.getUserByEmail(email);
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}