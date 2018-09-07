package com.dkt.breaking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

@RestController
public class SessionController {

    @RequestMapping("session")
    public void uid(WebSession session) {
        System.out.println(session.getId());
    }
}
