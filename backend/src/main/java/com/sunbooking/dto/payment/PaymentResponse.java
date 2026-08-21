package com.sunbooking.dto.payment;

import com.sunbooking.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private String bookingId;
    private BigDecimal amount;
    private String transactionReference;
    private PaymentStatus status;
    private String qrCodeUrl; // Generated URL for FE to display QR code
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt; // Reservation expiry time
}