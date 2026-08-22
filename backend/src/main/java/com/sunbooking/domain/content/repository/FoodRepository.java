package com.sunbooking.domain.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbooking.domain.content.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
	List<Food> findByStatusIgnoreCase(String status);
}