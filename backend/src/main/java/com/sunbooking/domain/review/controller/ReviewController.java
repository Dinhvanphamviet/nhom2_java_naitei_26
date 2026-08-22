package com.sunbooking.domain.review.controller;

import com.sunbooking.domain.review.dto.CreateReviewRequest;
import com.sunbooking.domain.review.dto.ReviewResponse;
import com.sunbooking.domain.review.service.ReviewService;
import com.sunbooking.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @RequestParam(required = false) Long tourId) {
        return ResponseEntity.ok(reviewService.getReviews(tourId));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateReviewRequest request) {
        ReviewResponse response = reviewService.createReview(userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
