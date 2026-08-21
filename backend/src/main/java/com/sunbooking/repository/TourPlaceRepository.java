package com.sunbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.entity.TourPlace;

public interface TourPlaceRepository extends JpaRepository<TourPlace, Long> {
}