package com.dkt.breaking.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @RabbitListener(queues = "hello")
    public void receive(Object message) {
        System.out.println(message);
    }

}
