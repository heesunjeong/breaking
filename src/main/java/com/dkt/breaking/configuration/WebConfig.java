package com.dkt.breaking.configuration;

import com.dkt.breaking.aspect.GreetingFilter;
import com.dkt.breaking.controller.WelcomeHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig {

    @Autowired
    WelcomeHandler welcomeHandler;

    @Autowired
    GreetingFilter greetingFilter;

    @Bean
    public RouterFunction<?> routes() {

        return route(GET("/hello/{id}").and(accept(APPLICATION_JSON)), welcomeHandler::sayHello).filter(greetingFilter.filter())
            .andRoute(POST("/hi/{id}").and(accept(APPLICATION_JSON)), welcomeHandler::sayHi);
    }
}
