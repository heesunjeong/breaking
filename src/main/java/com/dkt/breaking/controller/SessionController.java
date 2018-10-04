package com.dkt.breaking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import java.security.Principal;

@RestController
public class SessionController {

    @GetMapping("session")
    public void uid(WebSession session, Principal principal) {
        System.out.println(principal);
    }
}
