package com.dkt.breaking.controller;

import com.dkt.breaking.service.MapsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("maps")
public class MapsController {

    @Autowired
    private MapsService mapsService;

    @GetMapping(value = "location/{address}")
    public Object getAddress(@PathVariable String address) {
        return  mapsService.getAddress(address);
    }

    @PostMapping(value="place")
    public ResponseEntity getPlaceByKeyword(@RequestBody HashMap<String, String> query) {
        return ResponseEntity.ok(mapsService.getPlaceByKeyword(query));
    }
}
