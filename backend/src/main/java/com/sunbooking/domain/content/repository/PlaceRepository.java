package com.sunbooking.domain.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.domain.content.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}