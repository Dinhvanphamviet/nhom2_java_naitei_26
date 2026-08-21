package com.sunbooking.domain.payment.service;

import com.sunbooking.config.SePayConfig;
import com.sunbooking.domain.payment.dto.PaymentResponse;
import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.domain.payment.entity.Payment;
import com.sunbooking.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SePayConfig sePayConfig;

    @Transactional
    public PaymentResponse createPayment(Booking booking) {
        // 1. Generate unique reference for SePay matching
        String reference = sePayConfig.getQrPrefix() + booking.getId()
                + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        // 2. Create Payment entity (Status defaults to PENDING via @Builder.Default)
        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getTotalPrice()) // Price from Booking entity
                .transactionReference(reference)
                .paymentMethod("VIETQR")
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // 3. Generate VietQR Image URL
        String qrUrl = String.format("https://qr.sepay.vn/img?acc=%s&bank=%s&amount=%s&des=%s",
                sePayConfig.getBankAccount(),
                sePayConfig.getBankName(),
                savedPayment.getAmount().toPlainString(),
                reference);

        return mapToResponse(savedPayment, qrUrl);
    }

    private PaymentResponse mapToResponse(Payment payment, String qrUrl) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId().toString())
                .amount(payment.getAmount())
                .transactionReference(payment.getTransactionReference())
                .status(payment.getStatus())
                .qrCodeUrl(qrUrl)
                .createdAt(payment.getCreatedAt())
                .expiredAt(payment.getExpiredAt()) // From @Transient method in Entity
                .build();
    }
}