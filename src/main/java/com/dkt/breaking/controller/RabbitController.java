package com.dkt.breaking.controller;

import com.dkt.breaking.service.RabbitMQSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    @Autowired
    RabbitMQSender sender;

    @GetMapping("message")
    public void sendMessage() {
        sender.sending();

    }
}
