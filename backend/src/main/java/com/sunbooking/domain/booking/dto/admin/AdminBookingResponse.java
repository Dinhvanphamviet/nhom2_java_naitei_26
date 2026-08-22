package com.sunbooking.domain.booking.dto.admin;

import com.sunbooking.domain.booking.entity.BookingStatus;
import com.sunbooking.domain.booking.dto.BookingTravelerDto;
import com.sunbooking.domain.payment.dto.PaymentSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminBookingResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    
    private Long departureId;
    private Long tourId;
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
    
    private PaymentSummaryDto payment;
}
