package com.sunbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}