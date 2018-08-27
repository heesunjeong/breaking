package com.dkt.breaking.configuration.security;

import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BreakingUserDetailService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> data = userRepository.findByEmail(username);
        return data.cast(UserDetails.class);
    }
}
