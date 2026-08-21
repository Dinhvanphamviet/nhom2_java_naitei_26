package com.sunbooking.domain.tour.repository;

import com.sunbooking.domain.tour.entity.TourDeparture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDepartureRepository extends JpaRepository<TourDeparture, Long> {

    // Atomic update: only subtract if availableSlot >= requested slots
    @Modifying
    @Query("UPDATE TourDeparture d SET d.availableSlot = d.availableSlot - :slots " +
            "WHERE d.id = :id AND d.availableSlot >= :slots")
    int deductAvailableSlots(Long id, int slots);

    @Modifying
    @Query("UPDATE TourDeparture d SET d.availableSlot = d.availableSlot + :slots WHERE d.id = :id")
    int addAvailableSlots(Long id, int slots);
}