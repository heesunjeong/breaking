package com.dkt.breaking.service;

import com.dkt.breaking.model.Review;
import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.UnsupportedJwtException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    public Mono<Boolean> saveReview(Review review, ServerWebExchange exchange) {
        String userId = validateTokenAndGetUserId(exchange);

        review.setAuthor(new User(userId));
        return reviewRepository.save(review)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Flux<Review> getReviewList(Mono<String> storeKey, int page, int size) {
        return reviewRepository.findByStoreKeyAndDeletedFalse(storeKey, PageRequest.of(page, size));
    }

    public Mono<Boolean> updateReview(Review review, ServerWebExchange exchange) {
        String userId = validateTokenAndGetUserId(exchange);

        return reviewRepository.findById(review.getId())
            .filter(r -> r.getAuthor().getId().equals(userId))
            .flatMap(r -> {
                r.setContents(review.getContents());
                return reviewRepository.save(r);
            })
            .map(r -> true)
            .defaultIfEmpty(false);
    }

    public Mono<Boolean> deleteReview(String reviewId, ServerWebExchange exchange) {
        String userId = validateTokenAndGetUserId(exchange);

        return reviewRepository.findById(reviewId)
            .filter(r -> r.getAuthor().getId().equals(userId))
            .flatMap(r -> {
                r.setDeleted(true);
                return reviewRepository.save(r);
            })
            .map(r -> true)
            .defaultIfEmpty(false);
    }

    public Flux<Review> getReviewListByAuthor(String userId) {
        return reviewRepository.findByAuthor(Mono.just(userId))
            .filter(r -> !r.getDeleted());
    }

    private String validateTokenAndGetUserId(ServerWebExchange exchange) {
        String userId = userService.getUserIdByToken(exchange);

        if (userId == "" || userId.isEmpty()) {
            new UnsupportedJwtException("Not Supported Token Error");
        }
        return userId;
    }

    public Mono<Long> countReview(Mono<String> storeKey) {
        return reviewRepository.countByStoreKeyAndDeletedFalse(storeKey).defaultIfEmpty(0L);
    }
}
