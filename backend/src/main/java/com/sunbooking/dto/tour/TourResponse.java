package com.sunbooking.dto.tour;

import com.sunbooking.entity.Tour;
import com.sunbooking.entity.TourStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record TourResponse(
        Long id,
        String name,
        String description,
        BigDecimal basePrice,
        String departure,
        String destination,
        String duration,
        TourStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        CategoryResponse category,
        List<TourImageResponse> images,
        List<TourDepartureResponse> departures,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TourResponse from(Tour tour) {
        if (tour == null) {
            return null;
        }

        CategoryResponse category = tour.getCategory() == null
                ? null : CategoryResponse.from(tour.getCategory());
        List<TourImageResponse> images = Optional.ofNullable(tour.getImages()).orElseGet(List::of).stream()
                .map(image -> new TourImageResponse(image.getId(), image.getImageUrl()))
                .toList();
        List<TourDepartureResponse> departures = Optional.ofNullable(tour.getDepartures()).orElseGet(List::of).stream()
                .map(TourDepartureResponse::from)
                .toList();

        return new TourResponse(
                tour.getId(), tour.getName(), tour.getDescription(), tour.getBasePrice(),
                tour.getDeparture(), tour.getDestination(), tour.getDuration(), tour.getStatus(),
                tour.getStartDate(), tour.getEndDate(), category, images, departures,
                tour.getCreatedAt(), tour.getUpdatedAt()
        );
    }
}