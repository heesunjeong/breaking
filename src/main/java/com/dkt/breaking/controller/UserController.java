package com.dkt.breaking.controller;

import com.dkt.breaking.model.User;
import com.dkt.breaking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("save")
    public Mono<Boolean> addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("check/{email}")
    public Mono<Boolean> checkDuplication(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}