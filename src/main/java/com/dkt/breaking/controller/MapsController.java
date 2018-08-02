package com.dkt.breaking.controller;

import com.dkt.breaking.model.MapData;
import com.dkt.breaking.service.MapsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("maps")
public class MapsController {

    @Autowired
    private MapsService mapsService;

    @GetMapping(value = "location/{address}")
    public Mono<MapData> getAddress(@PathVariable String address) {
        return mapsService.getAddress(address);
    }

    @GetMapping(value = "place")
    public Mono<MapData> getPlaceByKeyword(@RequestParam MultiValueMap<String, String> param) {
        return mapsService.getPlaceByKeyword(param);
    }
}
