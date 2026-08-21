package com.sunbooking.domain.tour.service;

import com.sunbooking.domain.tour.repository.TourDepartureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CapacityService {

    private final TourDepartureRepository departureRepository;

    @Transactional
    public void reserveCapacity(Long departureId, int slots) {
        // Perform atomic update in DB
        int updatedRows = departureRepository.deductAvailableSlots(departureId, slots);

        // If no rows updated, it means ID not found or Not enough slots
        if (updatedRows == 0) {
            throw new RuntimeException("Booking failed: Not enough slots available");
        }
    }

    @Transactional
    public void releaseCapacity(Long departureId, int slots) {
        departureRepository.addAvailableSlots(departureId, slots);
    }
}