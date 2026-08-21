package com.sunbooking.dto.tour;

import com.sunbooking.entity.TourDepartureStatus;
import com.sunbooking.entity.TourStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TourRequest(
        @NotBlank @Size(max = 255) String name,
        String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal basePrice,
        String departure,
        String destination,
        String duration,
        @NotNull TourStatus status,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotNull Long categoryId,
        @Valid List<TourImageRequest> images,
        @Valid List<TourDepartureRequest> departures
) {

    public record TourImageRequest(
            @NotBlank @Size(max = 255) String imageUrl
    ) {
    }

    public record TourDepartureRequest(
            @NotNull LocalDate departureDate,
            @NotNull LocalDate returnDate,
            @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal price,
            @NotNull @Positive Integer totalSlot,
            @NotNull @PositiveOrZero Integer availableSlot,
            @NotNull TourDepartureStatus status
    ) {
    }
}