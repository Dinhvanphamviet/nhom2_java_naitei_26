package com.sunbooking.domain.booking.dto;

import com.sunbooking.domain.booking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long departureId;
    private String tourName;
    private LocalDate departureDate;
    private LocalDateTime bookingDate;
    private Integer numberOfPeople;
    private BigDecimal totalPrice;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private BookingStatus status;
    private List<BookingTravelerDto> travelers;
}
