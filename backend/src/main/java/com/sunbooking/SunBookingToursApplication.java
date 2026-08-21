package com.sunbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Add this to enable background tasks
public class SunBookingToursApplication {
    public static void main(String[] args) {
        SpringApplication.run(SunBookingToursApplication.class, args);
    }
}