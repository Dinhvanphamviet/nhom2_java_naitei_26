package com.sunbooking.domain.review.service;

import com.sunbooking.domain.review.dto.CreateReviewRequest;
import com.sunbooking.domain.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(Long userId, CreateReviewRequest request);

    List<ReviewResponse> getReviews(Long tourId);
}
