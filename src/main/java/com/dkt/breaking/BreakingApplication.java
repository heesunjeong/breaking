package com.dkt.breaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class BreakingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreakingApplication.class, args);
    }
}
