package com.dkt.breaking.controller;

import com.dkt.breaking.model.Review;
import com.dkt.breaking.service.ReviewService;
import com.dkt.breaking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.UnsupportedJwtException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;


    @PostMapping(value = "create")
    /*@PreAuthorize("hasRole('ROLE_USER')")*/
    public Mono<Boolean> insertReview(@RequestBody Review review, ServerWebExchange exchange) {
        String userName = userService.getUserNameByToken(exchange);

        if (userName == "") {
            return Mono.error(new UnsupportedJwtException("Not Supported Token Error"));
        } else {

            review.setAuthor(userService.getUserByEmail(userName));

            return reviewService.createReview(review);
        }
    }

    @GetMapping(value = "/{storeId}")
    public Flux<Review> roadReviews(@PathVariable String storeId) {
        return reviewService.readReviews(Mono.just(storeId));
    }
}
