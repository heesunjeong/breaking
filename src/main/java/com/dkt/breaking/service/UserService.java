package com.dkt.breaking.service;

import com.dkt.breaking.configuration.security.JwtTokenProvider;
import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Mono<Boolean> saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String getUserNameByToken(String authToken) {
        return jwtTokenProvider.getUsernameFromToken(authToken);
    }

    public String getUserIdByToken(String authToken) {
        return jwtTokenProvider.getAllClaimsFromToken(authToken).getId();
    }

    public String getJwtToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            return "";
        }
    }

    @PreAuthorize("hasPermission(#user, 'edit')")
    public Mono<Void> processUser(Mono<User> user) {
        return Mono.just(null);
    }
}
