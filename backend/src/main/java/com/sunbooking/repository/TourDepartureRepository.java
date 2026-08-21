package com.sunbooking.repository;

import com.sunbooking.entity.TourDeparture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourDepartureRepository extends JpaRepository<TourDeparture, Long> {
    List<TourDeparture> findByTourIdOrderByDepartureDateAsc(Long tourId);
}