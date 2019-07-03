package com.dkt.breaking.service;

import com.dkt.breaking.model.Review;
import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

import javax.validation.ValidationException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReviewRepository reviewRepository;

    public Flux<Review> getReviewList(String storeKey, Integer page, Integer size) {
        return reviewRepository.findByStoreKeyAndDeletedFalse(storeKey, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "reg_at")));
    }

    public Flux<Review> getReviewListByAuthor(String userId, Integer page, Integer size) {
        return reviewRepository.findByAuthorAndDeletedFalse(userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "reg_at")));
    }

    public Mono<Boolean> saveReview(Review review, ServerWebExchange exchange) {
        String userId = validateTokenAndGetUserId(exchange);

        return storeService.getStore(review.getStoreKey())
            .flatMap(store -> {
                System.out.println(store);

                review.setAuthor(new User(userId));
                review.setStore(store);
                return reviewRepository.save(review);
            })
            .map(s -> true)
            .defaultIfEmpty(false);
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

    private String validateTokenAndGetUserId(ServerWebExchange exchange) {

        return Optional.ofNullable(userService.getUserIdByToken(exchange))
            .orElseThrow(() -> new ValidationException());
    }

    public Mono<Long> countReview(String storeKey) {
        return reviewRepository.countByStoreKeyAndDeletedFalse(storeKey).defaultIfEmpty(0L);
    }
}
