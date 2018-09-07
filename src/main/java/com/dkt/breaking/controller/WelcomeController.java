package com.dkt.breaking.controller;

import com.dkt.breaking.model.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

@RestController
public class WelcomeController {

    @GetMapping(value = "/")
    public Mono<HelloWorld> sayHello(WebSession webSession) {
        HelloWorld hello = new HelloWorld();

        System.out.println(webSession);

        hello.setTitle("hello");
        hello.setMessage("hi");

        return Mono.just(hello);
    }
}
