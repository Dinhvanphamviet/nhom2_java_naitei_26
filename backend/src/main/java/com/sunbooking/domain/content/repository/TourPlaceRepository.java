package com.sunbooking.domain.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.domain.content.entity.TourPlace;

public interface TourPlaceRepository extends JpaRepository<TourPlace, Long> {
}