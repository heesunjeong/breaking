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
import org.springframework.web.bind.annotation.RequestParam;
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
    public Mono<Boolean> saveReview(@RequestBody Review review, ServerWebExchange exchange) {
        return reviewService.saveReview(review, exchange);
    }

    @GetMapping(value = "/list")
    public Flux<Review> getReviewList(@RequestParam String storeKey, @RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "5") Integer size) {
        return reviewService.getReviewList(Mono.just(storeKey), page, size);
    }

    @GetMapping(value = "/count")
    public Mono<Long> count(@RequestParam String storeKey) {
        return reviewService.countReview(Mono.just(storeKey));
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
