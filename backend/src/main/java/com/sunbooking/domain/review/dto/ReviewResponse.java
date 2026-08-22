package com.sunbooking.domain.review.dto;

import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.domain.review.entity.Review;
import com.sunbooking.domain.tour.entity.Tour;
import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record ReviewResponse(
        Long id,
        Long bookingId,
        Long tourId,
        String tourName,
        Long userId,
        String reviewerName,
        String reviewerAvatar,
        String content,
        Integer rating,
        List<ReviewImageResponse> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewResponse from(Review review) {
        Booking booking = review.getBooking();
        User user = booking != null ? booking.getUser() : null;
        TourDeparture departure = booking != null ? booking.getDeparture() : null;
        Tour tour = departure != null ? departure.getTour() : null;

        List<ReviewImageResponse> images = Optional.ofNullable(review.getImages())
                .orElseGet(List::of)
                .stream()
                .map(ReviewImageResponse::from)
                .toList();

        return new ReviewResponse(
                review.getId(),
                booking != null ? booking.getId() : null,
                tour != null ? tour.getId() : null,
                tour != null ? tour.getName() : null,
                user != null ? user.getId() : null,
                user != null ? user.getFullName() : null,
                user != null ? user.getAvatar() : null,
                review.getContent(),
                review.getRating(),
                images,
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
