package com.dkt.breaking.controller;

import com.dkt.breaking.model.Store;
import com.dkt.breaking.service.MapsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("maps")
public class MapsController {

    @Autowired
    private MapsService mapsService;

    @GetMapping(value = "location/{address}")
    public Mono<Map> getAddress(@PathVariable String address) {
        return mapsService.getAddress(address);
    }

    @GetMapping(value = "place")
    public Mono<Map> getPlaceByKeyword(@RequestParam MultiValueMap<String, String> param) {
        return mapsService.getPlaceByKeyword(param);
    }

    @PostMapping(value = "store")
    public Mono<Boolean> saveStore(@RequestBody Store store) {
        return mapsService.savePlace(store);
    }
}
