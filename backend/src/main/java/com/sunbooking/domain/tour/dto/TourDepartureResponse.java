package com.sunbooking.domain.tour.dto;

import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.domain.tour.entity.TourDepartureStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TourDepartureResponse(
        Long id,
        LocalDate departureDate,
        LocalDate returnDate,
        BigDecimal price,
        Integer totalSlot,
        Integer availableSlot,
        TourDepartureStatus status
) {
    public static TourDepartureResponse from(TourDeparture departure) {
        return new TourDepartureResponse(
                departure.getId(), departure.getDepartureDate(), departure.getReturnDate(),
                departure.getPrice(), departure.getTotalSlot(), departure.getAvailableSlot(),
                departure.getStatus()
        );
    }
}