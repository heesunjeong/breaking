package com.dkt.breaking.service;

import com.dkt.breaking.model.Review;
import com.dkt.breaking.persistence.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    public Mono<Boolean> createReview(Review review) {
        return reviewRepository.save(review)
            .map(u -> true)
            .defaultIfEmpty(false);
    }

    public Flux<Review> readReviews(Mono<String> storeId) {
        Map<String, String> reviewDetails = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return reviewRepository.findByStoreId(storeId).log();
    }

}
