package com.sunbooking.domain.payment.controller;

import com.sunbooking.domain.payment.entity.Payment;
import com.sunbooking.domain.payment.entity.PaymentStatus;
import com.sunbooking.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;

    @GetMapping("/{id}/status")
    public ResponseEntity<?> getPaymentStatus(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Return only status for FE polling
        return ResponseEntity.ok(Map.of("status", payment.getStatus()));
    }
}