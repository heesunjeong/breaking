package com.dkt.breaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.session.data.mongo.config.annotation.web.reactive.EnableMongoWebSession;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@EnableMongoWebSession
@SpringBootApplication
public class BreakingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreakingApplication.class, args);
    }
}
