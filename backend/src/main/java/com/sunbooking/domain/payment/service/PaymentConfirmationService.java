package com.sunbooking.domain.payment.service;

import com.sunbooking.domain.payment.dto.SePayWebhookRequest;
import com.sunbooking.domain.payment.entity.Payment;
import com.sunbooking.domain.payment.entity.PaymentStatus;
import com.sunbooking.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentConfirmationService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void confirmPayment(SePayWebhookRequest request) {
        // Find payment by reference (content field in SePay)
        Payment payment = paymentRepository.findByTransactionReference(request.getContent())
                .orElseThrow(() -> new RuntimeException("Payment reference not found"));

        // Idempotency check: Only process if PENDING
        if (payment.getStatus() != PaymentStatus.PENDING) {
            return;
        }

        // Update payment details
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setExternalTransactionId(request.getCode()); // Bank transaction code
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        // SUCCESS status here permanently secures the slots.
    }
}