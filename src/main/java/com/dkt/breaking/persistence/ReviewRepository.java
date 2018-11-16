package com.dkt.breaking.persistence;

import com.dkt.breaking.model.Review;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReviewRepository extends ReactiveCrudRepository<Review, String> {

    Flux<Review> findByStoreId(Mono<String> storeId);

    Flux<Review> findByAuthor(Mono<String> userId);
}
