package com.dkt.breaking.service;

import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<Boolean> saveUser(User user) {
        return userRepository.save(user)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<Boolean> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(u -> true)
            .defaultIfEmpty(false);
    }
}
