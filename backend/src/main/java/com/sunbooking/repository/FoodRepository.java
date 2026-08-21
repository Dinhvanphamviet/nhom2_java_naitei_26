package com.sunbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}