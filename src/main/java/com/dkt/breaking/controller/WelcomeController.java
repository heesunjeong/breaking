package com.dkt.breaking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping(value = "/")
    public String sayHello() {
        return "Hello BREAK world!";
    }
}
