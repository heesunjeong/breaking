package com.dkt.breaking.controller;

import com.dkt.breaking.model.Store;
import com.dkt.breaking.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping(value = "save")
    public Mono<Boolean> saveStore(@RequestBody Store store) {
        return storeService.saveStore(store);
    }

    @GetMapping(value="info")
    public Mono<Store> getStore(@RequestParam String storeKey) {
        return storeService.getStore(storeKey);
    }
}
