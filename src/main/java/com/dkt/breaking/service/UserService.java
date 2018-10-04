package com.dkt.breaking.service;

import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<Boolean> saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Mono<Boolean> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Mono<String> getUserNameById(String id) {
        return userRepository.findUserById(id)
            .map(user -> user.getName())
            .defaultIfEmpty(null);
    }

    @PreAuthorize("hasPermission(#user, 'edit')")
    public Mono<Void> processUser(Mono<User> user) {
        return Mono.just(null);
    }
}
