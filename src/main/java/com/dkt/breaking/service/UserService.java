package com.dkt.breaking.service;

import com.dkt.breaking.configuration.security.BreakingUserDetailService;
import com.dkt.breaking.configuration.security.BreakingUserDetails;
import com.dkt.breaking.configuration.security.JwtTokenProvider;
import com.dkt.breaking.configuration.security.UserRole;
import com.dkt.breaking.model.AuthResponse;
import com.dkt.breaking.model.Passwords;
import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.UUID;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private BreakingUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Mono<AuthResponse> login(User user) {
        return userDetailService.findByUsername(user.getEmail())
            .filter(userDetails -> passwordEncoder.matches(user.getPassword(), userDetails.getPassword()))
            .map(userDetails -> {
                User userInfo = ((BreakingUserDetails) userDetails).getUser();
                return new AuthResponse(jwtTokenProvider.generateToken(userDetails), userInfo.getName(), userInfo.getId());
            })
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid User Info")))
            .onErrorResume(UsernameNotFoundException.class,
                e -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Found User", e)));
    }

    public Mono<Boolean> addUser(User user) {
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Mono<User> getUserById(String userId, ServerWebExchange exchange) {
        if (userId.equals("0")) {
            userId = getUserIdByToken(getJwtToken(exchange));
        }

        return userRepository.findById(userId);
    }

    public Mono<Boolean> updateUser(User user, ServerWebExchange exchange) {
        String userId = getUserIdByToken(getJwtToken(exchange));

        return userRepository.findById(user.getId())
            .filter(u -> u.getId().equals(userId))
            .flatMap(u -> {
                u.setName(user.getName());
                u.setGender(user.getGender());
                u.setBirth(user.getBirth());
                u.setBio(user.getBio());

                return userRepository.save(u);
            })
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Mono<Boolean> updatePassword(Passwords passwords, ServerWebExchange exchange) {
        String userToken = getJwtToken(exchange);

        return userRepository.findById(getUserIdByToken(userToken))
            .filter(u -> passwordEncoder.matches(passwords.getPassword(), u.getPassword()))
            .flatMap(u -> {
                u.setPassword(new BCryptPasswordEncoder().encode(passwords.getNewPassword()));
                return userRepository.save(u);
            })
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public String getUserIdByToken(String authToken) {
        return String.valueOf(jwtTokenProvider.getAllClaimsFromToken(authToken).get("id"));
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

    public Mono<Boolean> findPassword(String email) {
        String newPassword = UUID.randomUUID().toString().replaceAll("-", "");

        return userRepository.findByEmail(email)
            .flatMap(u -> {
                u.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                return userRepository.save(u);
            })
            .map(u -> emailService.sendSimpleMessage(email, "[우리 오늘 맛집 뿌셔?] 새로운 비밀번호입니다.", String.format("새로운 비밀번호는 %s 입니다.", newPassword)))
            .defaultIfEmpty(false);
    }

    @PreAuthorize("hasPermission(#user, 'edit')")
    public Mono<Void> processUser(Mono<User> user) {
        return Mono.just(null);
    }
}
