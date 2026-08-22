package com.sunbooking.domain.review.service;

import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.domain.booking.entity.BookingStatus;
import com.sunbooking.domain.booking.repository.BookingRepository;
import com.sunbooking.domain.review.dto.CreateReviewRequest;
import com.sunbooking.domain.review.dto.ReviewResponse;
import com.sunbooking.domain.review.entity.Review;
import com.sunbooking.domain.review.entity.ReviewImage;
import com.sunbooking.domain.review.repository.ReviewRepository;
import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(Long userId, CreateReviewRequest request) {
        Booking booking = bookingRepository.findByIdWithDetails(request.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You can only review your own booking");
        }

        validateBookingIsCompleted(booking);

        if (reviewRepository.existsByBooking_Id(booking.getId())) {
            throw new IllegalArgumentException("This booking already has a review");
        }

        Review review = Review.builder()
                .booking(booking)
                .content(request.content())
                .rating(request.rating())
                .build();

        if (request.imageUrls() != null) {
            request.imageUrls().forEach(imageUrl -> review.addImage(
                    ReviewImage.builder()
                            .imageUrl(imageUrl)
                            .build()
            ));
        }

        try {
            Review savedReview = reviewRepository.saveAndFlush(review);
            return ReviewResponse.from(savedReview);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("This booking already has a review");
        }
    }

    private void validateBookingIsCompleted(Booking booking) {
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only confirmed bookings can be reviewed");
        }

        TourDeparture departure = booking.getDeparture();
        if (departure == null
                || departure.getReturnDate() == null
                || !departure.getReturnDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("You can only review a tour after it has finished");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews(Long tourId) {
        List<Review> reviews = tourId == null
                ? reviewRepository.findAllWithDetails()
                : reviewRepository.findByTourIdWithDetails(tourId);

        return reviews.stream()
                .map(ReviewResponse::from)
                .toList();
    }
}
