package com.dkt.breaking.persistence;

import com.dkt.breaking.model.Store;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface StoreRepository extends ReactiveCrudRepository<Store, String> {

    Mono<Boolean> existsByStoreKey(String storeKey);

    Mono<Long> countByStoreKey(String storeKey);

    Mono<Store> findByStoreKey(String storeKey);
}
