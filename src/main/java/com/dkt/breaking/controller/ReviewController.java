package com.dkt.breaking.controller;

import com.dkt.breaking.model.Review;
import com.dkt.breaking.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "create")
    /*@PreAuthorize("hasRole('ROLE_USER')")*/
    public Mono<Boolean> addReview(@RequestBody Review review, ServerWebExchange exchange) {
        return reviewService.addReview(review, exchange);
    }

    @GetMapping(value = "/{storeId}")
    public Flux<Review> getReviewList(@PathVariable String storeId) {
        return reviewService.getReviewList(Mono.just(storeId));
    }

    @PutMapping(value = "/update")
    public Mono<Boolean> updateReview(@RequestBody Review review, ServerWebExchange exchange) {
        return reviewService.updateReview(review, exchange);
    }

    @DeleteMapping(value = "/{reviewId}")
    public Mono<Boolean> deleteReview(@PathVariable String reviewId, ServerWebExchange exchange) {
        return reviewService.deleteReview(reviewId, exchange);
    }

    @GetMapping(value = "/author/{userId}")
    public Flux<Review> getReviewByAuthor(@PathVariable String userId) {
        return reviewService.getReviewListByAuthor(userId);
    }
}
