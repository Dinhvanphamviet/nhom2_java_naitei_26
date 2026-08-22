package com.sunbooking.domain.payment.dto;

import com.sunbooking.domain.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSummaryDto {
    private PaymentStatus status;
    private BigDecimal amount;
    private String transactionReference;
    private LocalDateTime paidAt;
}
