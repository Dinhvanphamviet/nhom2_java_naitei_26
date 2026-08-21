package com.sunbooking.domain.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.global.common.BaseEntity;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_reference", nullable = false, unique = true)
    private String transactionReference; // Internal unique ref for SePay matching

    @Column(name = "external_transaction_id")
    private String externalTransactionId; // Transaction ID from Bank/SePay

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "payment_method")
    private String paymentMethod; // Default is VIETQR

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Transient // JPA ignores this field in DB mapping
    public LocalDateTime getExpiredAt() {
        if (this.getCreatedAt() == null)
            return null;
        return this.getCreatedAt().plusMinutes(15);
    }
}