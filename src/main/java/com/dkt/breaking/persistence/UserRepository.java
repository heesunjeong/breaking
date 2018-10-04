package com.dkt.breaking.persistence;

import com.dkt.breaking.configuration.security.BreakingUserDetails;
import com.dkt.breaking.model.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<BreakingUserDetails> findByEmail(String name);

    Mono<User> findUserById(String id);
}
