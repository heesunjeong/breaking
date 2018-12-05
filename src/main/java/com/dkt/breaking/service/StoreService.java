package com.dkt.breaking.service;

import com.dkt.breaking.model.Store;
import com.dkt.breaking.persistence.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Mono<Store> getStore(String storeKey) {
        return storeRepository.findByStoreKey(storeKey);
    }

    public Mono<Boolean> saveStore(Store store) {
        return storeRepository.countByStoreKey(store.getStoreKey())
            .filter(place -> place == 0)
            .flatMap(place -> storeRepository.save(store))
            .map(place -> true)
            .defaultIfEmpty(false);
    }
}
