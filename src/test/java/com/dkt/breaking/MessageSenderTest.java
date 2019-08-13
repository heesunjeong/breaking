package com.dkt.breaking;

import com.dkt.breaking.service.RabbitMQSender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageSenderTest {

    @Autowired
    private RabbitMQSender sender;

    @Test
    public void senderTest() {
        System.out.println("start!");
        sender.sending();
    }
}
