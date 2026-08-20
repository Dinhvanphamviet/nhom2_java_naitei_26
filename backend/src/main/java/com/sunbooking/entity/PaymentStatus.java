package com.sunbooking.entity;

public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED,
    EXPIRED // Payment timeout after 15 mins
}