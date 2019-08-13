package com.dkt.breaking.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate template;

    private final String EXCHANGE = "amq.direct";
    private final String ROUTING_KEY = "foo.test";

    public boolean sending() {
        template.convertAndSend(EXCHANGE, ROUTING_KEY, "Hello world!");
        return true;
    }
}
