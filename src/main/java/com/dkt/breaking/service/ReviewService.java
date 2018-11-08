package com.dkt.breaking.service;

import com.dkt.breaking.model.Review;
import com.dkt.breaking.model.User;
import com.dkt.breaking.persistence.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Mono<Boolean> createReview(Review review, ServerWebExchange exchange) {

        String userId = userService.getUserNameByToken(userService.getJwtToken(exchange));

        if (userId == "") {
            return Mono.error(new UnsupportedJwtException("Not Supported Token Error"));
        } else {
            User userInfo = new User();
            review.setAuthor(userInfo);
            return reviewRepository.save(review)
                .map(u -> true)
                .defaultIfEmpty(false);
        }
    }

    public Flux<Review> readReviews(Mono<String> storeId) {
        return reviewRepository.findByStoreId(storeId)
            .filter(review -> !review.getDeleted())
            .log();
    }
}
